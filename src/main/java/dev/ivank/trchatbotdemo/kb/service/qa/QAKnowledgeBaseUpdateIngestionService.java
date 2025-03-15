package dev.ivank.trchatbotdemo.kb.service.qa;

import dev.ivank.trchatbotdemo.common.i18n.Language;
import dev.ivank.trchatbotdemo.kb.ctx.KnowledgeBaseOperationContext;
import dev.ivank.trchatbotdemo.kb.dto.QAUpdateDto;
import dev.ivank.trchatbotdemo.common.i18n.translate.TranslationService;
import dev.ivank.trchatbotdemo.kb.EmbeddingEntityType;
import dev.ivank.trchatbotdemo.kb.service.KnowledgeBaseUpdateIngestionService;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class QAKnowledgeBaseUpdateIngestionService extends KnowledgeBaseUpdateIngestionService<QAUpdateDto, KnowledgeBaseOperationContext<QAUpdateDto>> {
    @Autowired
    protected QAKnowledgeBaseUpdateIngestionService(VectorStore vectorStore, TranslationService updateTranslator) {
        super(vectorStore, updateTranslator);
    }

    @Override
    public List<Document> ingest(KnowledgeBaseOperationContext<QAUpdateDto> ctx) {
        List<QAUpdateDto> input = ctx.getInput();
        List<Document> documentsToStore = new ArrayList<>();

        for (QAUpdateDto qa : input) {
            Language originalLanguage = detectLanguage(qa.getQ());

            if (originalLanguage.isValid()) {
                Document original = createDocument(qa, originalLanguage);
                original.getMetadata().put("ogLanguage", true);
                documentsToStore.add(original);

                /*
                 * If QAs are passed for modification, do not translate them
                 */
                if (qa.isToSave() && ctx.isTranslateToSupported()) {
                    Language.supported().stream()
                            .filter(lang -> !lang.equals(originalLanguage))
                            .forEach(lang -> {
                                String langToTranslateAlias = lang.getAlias();
                                String originalLanguageAlias = originalLanguage.getAlias();

                                try {
                                    String translatedQuestion = updateTranslator.translate(
                                            qa.getQ(),
                                            originalLanguageAlias,
                                            langToTranslateAlias
                                    );
                                    String translatedAnswer = updateTranslator.translate(
                                            qa.getA(),
                                            originalLanguageAlias,
                                            langToTranslateAlias
                                    );

                                    Document translated = createDocument(new QAUpdateDto(translatedQuestion, translatedAnswer), lang, true);
                                    translated.getMetadata().put("ogQa", original.getId());
                                    documentsToStore.add(translated);

                                } catch (Exception e) {
                                    LOGGER.error("Translation failed for language {}: {}", langToTranslateAlias, e.getMessage());
                                }
                            });
                }
            }
        }

        vectorStore.accept(documentsToStore);
        LOGGER.info("VectorStore Loaded with {} QA entries.", documentsToStore.size());
        return documentsToStore;
    }

    private Document createDocument(QAUpdateDto dto, Language language) {
        return createDocument(dto, language, false);
    }

    private Document createDocument(QAUpdateDto dto, Language language, boolean translated) {
        String question = dto.getQ();

        Map<String, Object> metadata = new java.util.HashMap<>(Map.of(
                "question", question,
                "answer", dto.getA(),
                "language", language.getAlias(),
                EmbeddingEntityType.EM, EmbeddingEntityType.QA.getCode()
        ));

        if (translated) {
            metadata.put("translated", true);
        }

        UUID vectorStoreId = dto.getVectorStoreId();
        return vectorStoreId == null ?
                new Document(question, metadata) :
                new Document(String.valueOf(vectorStoreId), question, metadata);
    }
}
