package dev.ivank.trchatbotdemo.kb.service;

import dev.ivank.trchatbotdemo.common.i18n.Language;
import dev.ivank.trchatbotdemo.common.i18n.translate.TranslationService;
import dev.ivank.trchatbotdemo.security.auth.EmployeeOnly;
import dev.ivank.trchatbotdemo.kb.ctx.KnowledgeBaseOperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

@EmployeeOnly
public abstract class KnowledgeBaseUpdateIngestionService<S, CONTEXT extends KnowledgeBaseOperationContext<S>> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseUpdateIngestionService.class);

    protected final VectorStore vectorStore;
    protected final TranslationService updateTranslator;

    protected KnowledgeBaseUpdateIngestionService(VectorStore vectorStore, TranslationService updateTranslator) {
        this.vectorStore = vectorStore;
        this.updateTranslator = updateTranslator;
    }

    public abstract List<Document> ingest(CONTEXT ctx);

    protected Language detectLanguage(String text) {
        try {
            return updateTranslator.detectLanguage(text);
        } catch (Exception e) {
            LOGGER.error("Error detecting text language: {}", text);
            return Language.UNDEFINED;
        }
    }
}
