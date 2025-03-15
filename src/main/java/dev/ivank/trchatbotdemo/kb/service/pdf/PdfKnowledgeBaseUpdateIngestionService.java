package dev.ivank.trchatbotdemo.kb.service.pdf;

import dev.ivank.trchatbotdemo.common.i18n.Language;
import dev.ivank.trchatbotdemo.common.io.StoredFile;
import dev.ivank.trchatbotdemo.common.i18n.translate.TranslationService;
import dev.ivank.trchatbotdemo.common.rag.RagUtils;
import dev.ivank.trchatbotdemo.kb.EmbeddingEntityType;
import dev.ivank.trchatbotdemo.kb.service.KnowledgeBaseUpdateIngestionService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PdfKnowledgeBaseUpdateIngestionService extends KnowledgeBaseUpdateIngestionService<StoredFile, PdfKnowledgeBaseOperationContext> {
    @Value("${file.upload.directory}")
    private String uploadDirPath;

    @Autowired
    protected PdfKnowledgeBaseUpdateIngestionService(VectorStore vectorStore, TranslationService updateTranslator) {
        super(vectorStore, updateTranslator);
    }

    @Override
    public List<Document> ingest(PdfKnowledgeBaseOperationContext ctx) {
        final List<Document> res = new ArrayList<>();

        ctx.getFileNamesMappings().forEach((originalFileName, filePath) -> {
            try (InputStream inputStream = new FileInputStream(filePath)) {
                // TODO: test this with TikaDocumentReader
                var pdfReader = new ParagraphPdfDocumentReader(new InputStreamResource(inputStream) {
                    @Override
                    public String getFilename() {
                        return getFilenameFromPath(filePath);
                    }
                });

                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> extractedDocuments = textSplitter.apply(pdfReader.get());

                for (Document doc : extractedDocuments) {
                    String text = normalize(doc.getText());
                    Language originalLanguage = detectLanguage(text);

                    if (originalLanguage.isValid()) {
                        long fileSize = Files.size(Paths.get(filePath));
                        Map<String, Object> metadata = doc.getMetadata();

                        metadata.put("file_size", fileSize);
                        metadata.put("language", originalLanguage.getAlias());
                        metadata.put("ogLanguage", true);
                        metadata.put(EmbeddingEntityType.EM, EmbeddingEntityType.PDF.getCode());

                        res.add(doc);

                        if (ctx.isTranslateToSupported()) {
                            res.addAll(translateIntoSupported(doc, originalLanguage, text));
                        }
                    }
                }

                vectorStore.accept(res);
                LOGGER.info("VectorStore Loaded with {} documents from PDF: {}", res.size(), originalFileName);

            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage());
            }
        });

        return res;
    }

    private static String normalize(String text) {
        text = text.replaceAll("[•\\-*\\d]+", "");
        text = text.replaceAll("\\p{C}", "");

        text = text.replaceAll("\\s{2,}", " ");
        text = text.trim();

        text = text.replaceAll("(?m)^[\\t ]*$", "");

        return text;
    }

    private String getFilenameFromPath(String filePath) {
        Path uploadDir = Paths.get(uploadDirPath).toAbsolutePath().normalize();
        Path file = Paths.get(filePath).toAbsolutePath().normalize();

        return uploadDir.relativize(file).toString();
    }

    private List<Document> translateIntoSupported(Document ogDoc, Language originalLanguage, String text) {
        final List<Document> res = new ArrayList<>();

        Language.supported().stream()
                .filter(lang -> !lang.equals(originalLanguage))
                .forEach(lang -> {
                    try {
                        String translatedText = updateTranslator.translate(
                                text, originalLanguage.getAlias(), lang.getAlias()
                        );

                        Map<String, Object> metadata = new HashMap<>();
                        metadata.put("language", lang.getAlias());
                        metadata.put("translated", true);
                        metadata.putAll(RagUtils.getFilteredOgDocMetadata(ogDoc, "language", "ogLanguage"));

                        Document translatedDoc = new Document(
                                translatedText,
                                metadata
                        );

                        res.add(translatedDoc);
                    } catch (Exception e) {
                        LOGGER.error("Translation failed for language {}: {}", lang.getAlias(), e.getMessage());
                    }
                });

        return res;
    }
}
