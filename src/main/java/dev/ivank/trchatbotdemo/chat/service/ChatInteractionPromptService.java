package dev.ivank.trchatbotdemo.chat.service;

import dev.ivank.trchatbotdemo.AiConfiguration;
import dev.ivank.trchatbotdemo.security.auth.VisitorOnly;
import dev.ivank.trchatbotdemo.chat.dto.ChatInteractionDto;
import dev.ivank.trchatbotdemo.chat.dto.ChatInteractionRequest;
import dev.ivank.trchatbotdemo.common.i18n.Language;
import dev.ivank.trchatbotdemo.common.i18n.translate.TranslationService;
import dev.ivank.trchatbotdemo.common.rag.RagUtils;
import dev.ivank.trchatbotdemo.kb.EmbeddingEntityType;
import dev.ivank.trchatbotdemo.report.domain.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ChatInteractionPromptService {
    private final ChatInteractionRecordService chatInteractionRecordService;
    private final TranslationService translationService;
    private final ChatLocalizationService localizationService;
    private final ChatClient chatClient;
    private final PgVectorStore vectorStore;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatInteractionPromptService.class);

    private static final double RELEVANCE_THRESHOLD = 0.7;
    private static final int TOP_K = 5;
    private static final int EXACT_MATCHING_EMBEDDING_THRESHOLD = 3;

    @Autowired
    public ChatInteractionPromptService(ChatInteractionRecordService chatInteractionRecordService,
                                        TranslationService translationService,
                                        ChatLocalizationService localizationService,
                                        @Qualifier(AiConfiguration.CHAT_MODEL) ChatClient chatClient,
                                        PgVectorStore vectorStore) {
        this.chatInteractionRecordService = chatInteractionRecordService;
        this.translationService = translationService;
        this.localizationService = localizationService;
        this.vectorStore = vectorStore;
        this.chatClient = chatClient;
    }

    /**
     * Initiates a chat interaction with the user, processing their prompt and generating a response.
     *
     * <p>This method performs the following steps:</p>
     * <ol>
     * <li><strong>Detect User Language:</strong> Determines the language of the user's prompt using the
     * {@link TranslationService}. If the language cannot be detected, a message indicating
     * language unavailability is returned.</li>
     * <li><strong>Perform Similarity Search:</strong> Executes a similarity search on the vector database
     * ({@link PgVectorStore}) to find relevant context for the user's prompt. This is a blocking
     * operation performed on a bounded elastic scheduler to prevent blocking the main thread.</li>
     * <li><strong>Handle Search Results:</strong> Processes the results of the similarity search.
     * <ul>
     * <li><strong>No Context Found:</strong> If no relevant context is found, a "no context" message
     * is returned to the user.</li>
     * <li><strong>Context Found:</strong> If relevant context is found, it is optimized for relevance
     * and language compatibility.</li>
     * </ul>
     * </li>
     * <li><strong>Optimize Results:</strong> Optimizes the search results by prioritizing QA entries over PDF
     * entries, sorting by language match, and translating embeddings as needed to match the user's
     * language.</li>
     * <li><strong>Generate Response:</strong> Constructs a prompt for the language model (LLM) using the
     * optimized context and the user's prompt. The LLM generates a streamed response, which is
     * processed asynchronously.</li>
     * <li><strong>Stream Response to Client:</strong> Streams the chunks of the LLM's response back to the
     * client as they are generated, without waiting for the full response to be assembled.</li>
     * <li><strong>Record Chat Interaction:</strong> After the LLM's response stream is complete, the full
     * response is recorded using the {@link ChatInteractionRecordService}. This is a blocking
     * operation performed on a bounded elastic scheduler.</li>
     * <li><strong>Save chat interaction into session</strong>
     * Updates the {@link Report} object in user session with the chat interaction.</li>
     * <li><strong>Return Response:</strong> Returns a {@link Flux} of {@link String} representing the
     * streamed response from the LLM.</li>
     * </ol>
     *
     * @param prompt        The user's input prompt.
     * @param currentReport The current report associated with the chat interaction.
     * @param sessionSave   A callback to update the current report state in user session.
     * @return A {@link Flux} of {@link ChatInteractionDto} representing the streamed response from the LLM.
     */
    @VisitorOnly
    public Flux<ChatInteractionDto> chat(String prompt, Report currentReport, Consumer<Report> sessionSave) {
        Instant askedAt = Instant.now();
        Language userLanguage = detectUserLanguage(prompt);

        if (!userLanguage.isValid()) {
            String langUndetectable = localizationService.getMessage("langUndetectable");
            final String chatInteractionDtoId = "chat-interaction-%s".formatted(UUID.randomUUID());

            return Flux.just(getChatInteractionDto(prompt, langUndetectable, Language.UNDEFINED, 0, askedAt, chatInteractionDtoId));
        }

        final long startTime = System.nanoTime();

        SearchRequest searchRequest = SearchRequest.builder()
                .query(prompt)
                .topK(TOP_K)
                .similarityThreshold(RELEVANCE_THRESHOLD)
                .build();

        ChatInteractionRequest request = new ChatInteractionRequest();

        request.setCurrentReport(currentReport);
        request.setUserLanguage(userLanguage);
        request.setUserPrompt(prompt);
        request.setAskedAt(askedAt);

        return Mono.fromCallable(() -> vectorStore.doSimilaritySearch(searchRequest))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(results -> handleSearchResults(
                        results,
                        request,
                        startTime,
                        sessionSave
                ));
    }

    private Flux<ChatInteractionDto> handleSearchResults(List<Document> results, ChatInteractionRequest request,
                                                         long startTime, Consumer<Report> sessionSave) {
        Language userLanguage = request.getUserLanguage();

        final String chatInteractionDtoId = "chat-interaction-" + UUID.randomUUID();
        if (results.isEmpty()) {
            String response = localizationService.getMessage("noContext", userLanguage.getLocale());

            request.setNoContext(true);

            return recordInteraction(request, response, startTime, sessionSave)
                    .thenMany(Flux.just(getChatInteractionDto(
                            request.getUserPrompt(),
                            response,
                            userLanguage,
                            startTime,
                            request.getAskedAt(),
                            chatInteractionDtoId))
                    );
        }

        List<Document> optimized = optimizeResults(results, userLanguage);

        request.setNoContext(false);

        return generateResponse(optimized, request.getUserPrompt(), userLanguage)
                .map(r -> getChatInteractionDto(
                        request.getUserPrompt(),
                        r,
                        userLanguage,
                        startTime,
                        request.getAskedAt(),
                        chatInteractionDtoId)
                )
                .collectList()
                .flatMapMany(chatInteractionDtos -> {
                    String fullResponse = chatInteractionDtos.stream()
                            .map(ChatInteractionDto::getAnswer)
                            .collect(Collectors.joining());
                    return recordInteraction(request, fullResponse, startTime, sessionSave)
                            .thenMany(Flux.fromIterable(chatInteractionDtos));
                });
    }

    private Mono<Void> recordInteraction(ChatInteractionRequest request, String response,
                                         double startTime, Consumer<Report> sessionSave) {
        return Mono.fromCallable(() -> {
                    request.setTimeToProcess(getElapsedSeconds(startTime));
                    request.setResponse(response);
                    return chatInteractionRecordService.recordChatInteraction(request);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(sessionSave)
                .then();
    }

    private static double getElapsedSeconds(final double startTime) {
        return (System.nanoTime() - startTime) / 1_000_000_000;
    }

    private static ChatInteractionDto getChatInteractionDto(String prompt, String answer,
                                                            Language language, double startTime,
                                                            Instant askedAt, String id) {
        ChatInteractionDto dto = new ChatInteractionDto();
        dto.setId(id);
        dto.setQuestion(prompt);
        dto.setAnswer(answer);
        dto.setTimeToProcess(getElapsedSeconds(startTime));
        dto.setQuestionLanguage(language);
        dto.setAskedAt(askedAt);
        return dto;
    }

    private Language detectUserLanguage(String prompt) {
        try {
            return translationService.detectLanguage(prompt);
        } catch (Exception e) {
            LOGGER.error("Error detecting user language: {}. Exception: {}", prompt, e.getLocalizedMessage());
            return Language.UNDEFINED;
        }
    }

    private Flux<String> generateResponse(List<Document> results, String userPrompt, Language userLanguage) {
        String context = results.stream()
                .map(searchResult -> {
                    Map<String, Object> metadata = searchResult.getMetadata();
                    EmbeddingEntityType embeddingEntityType = getEmbeddingEntityType(metadata);

                    if (embeddingEntityType.isQA()) {
                        String question = metadata.get("question").toString();
                        String answer = metadata.get("answer").toString();
                        return question + " " + answer;
                    } else if (embeddingEntityType.isPdf()) {
                        return searchResult.getText();
                    } else {
                        return "";
                    }
                })
                .collect(StringBuilder::new,
                        (sb, s) -> sb.append(s).append("\n\n"),
                        StringBuilder::append)
                .toString();

        //String sanitizedContext = context.replaceAll("[^\\p{Print}\\s]", "");

        String promptTemplate = localizationService.getMessage(
                "useContext",
                userLanguage.getLocale(),
                context,
                userPrompt
        );

        LOGGER.info("Passing a prompt to LLM: {}", promptTemplate);

        Prompt prompt = new Prompt(promptTemplate);
        return chatClient.prompt(prompt).stream().content();
    }

    private static EmbeddingEntityType getEmbeddingEntityType(Map<String, Object> metadata) {
        int emType = (int) metadata.getOrDefault(EmbeddingEntityType.EM, EmbeddingEntityType.UNDEFINED.getCode());
        return EmbeddingEntityType.byCode(emType);
    }

    private List<Document> optimizeResults(List<Document> results, Language userLanguage) {
        List<Document> sorted = results
                .stream()
                // Prioritize QA over PDF
                .sorted(Comparator.comparingInt(doc ->
                        (int) doc
                                .getMetadata()
                                .getOrDefault(EmbeddingEntityType.EM, EmbeddingEntityType.UNDEFINED.getCode()))
                )
                .sorted(getDocumentSortingComparator(userLanguage))
                .toList();

        long matchUserLanguageCount = sorted.stream()
                .filter(doc -> extractLanguageFromMetadata(doc).equals(userLanguage.getAlias()))
                .count();

        if (matchUserLanguageCount >= EXACT_MATCHING_EMBEDDING_THRESHOLD) {
            return sorted.stream().limit(matchUserLanguageCount).collect(Collectors.toList());
        }

        return sorted.stream().map(translateEmbeddings(userLanguage)).toList();
    }

    private Function<Document, Document> translateEmbeddings(Language userLanguage) {
        return doc -> {
            String docLang = extractLanguageFromMetadata(doc);
            if (!docLang.equals(userLanguage.getAlias())) {
                try {
                    Map<String, Object> metadata = doc.getMetadata();
                    EmbeddingEntityType embeddingEntityType = getEmbeddingEntityType(metadata);

                    if (embeddingEntityType.isQA()) {
                        String translatedQuestion = translationService.translate(
                                doc.getMetadata().get("question").toString(),
                                docLang,
                                userLanguage.getAlias()
                        );

                        String translatedAnswer = translationService.translate(
                                doc.getMetadata().get("answer").toString(),
                                docLang,
                                userLanguage.getAlias()
                        );

                        return new Document(translatedQuestion,
                                Map.of(
                                        "question", translatedQuestion,
                                        "answer", translatedAnswer,
                                        "language", userLanguage.getCode()
                                )
                        );
                    } else if (embeddingEntityType.isPdf()) {
                        String translatedContent = translationService.translate(
                                doc.getText(),
                                docLang,
                                userLanguage.getAlias()
                        );
                        Map<String, Object> translatedDocMd = new HashMap<>(
                                RagUtils.getFilteredOgDocMetadata(metadata, "content", "language")
                        );
                        translatedDocMd.put("content", translatedContent);
                        translatedDocMd.put("language", userLanguage.getAlias());
                        return new Document(translatedContent, translatedDocMd);
                    }
                } catch (Exception e) {
                    LOGGER.error(
                            "Error translating document metadata from {} to {}: {}",
                            docLang,
                            userLanguage.getCode(),
                            e.getMessage()
                    );
                }
            }
            return doc;
        };
    }

    private String extractLanguageFromMetadata(Document document) {
        return document.getMetadata().get("language").toString();
    }

    private Comparator<Document> getDocumentSortingComparator(Language userLanguage) {
        return (o1, o2) -> {
            String lang1 = o1.getMetadata().get("language").toString();
            String lang2 = o2.getMetadata().get("language").toString();

            boolean match1 = lang1.equals(userLanguage.getCode());
            boolean match2 = lang2.equals(userLanguage.getCode());

            return match1 == match2 ? 0 : (match1 ? -1 : 1);
        };
    }
}
