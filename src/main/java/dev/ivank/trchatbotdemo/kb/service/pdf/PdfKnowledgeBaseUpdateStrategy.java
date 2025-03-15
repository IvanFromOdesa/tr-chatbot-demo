package dev.ivank.trchatbotdemo.kb.service.pdf;

import dev.ivank.trchatbotdemo.common.io.FileStorageService;
import dev.ivank.trchatbotdemo.common.io.PdfProcessingService;
import dev.ivank.trchatbotdemo.common.io.StoredFile;
import dev.ivank.trchatbotdemo.kb.service.KnowledgeBaseOperationType;
import dev.ivank.trchatbotdemo.kb.service.KnowledgeBaseUpdateStrategy;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PdfKnowledgeBaseUpdateStrategy implements KnowledgeBaseUpdateStrategy<StoredFile, PdfKnowledgeBaseOperationContext> {
    private final PdfKnowledgeBaseUpdateIngestionService ingestionService;
    private final PdfContextOperationRecordService recordService;
    private final FileStorageService storageService;
    private final PdfProcessingService pdfProcessingService;

    @Autowired
    public PdfKnowledgeBaseUpdateStrategy(PdfKnowledgeBaseUpdateIngestionService ingestionService,
                                          PdfContextOperationRecordService recordService,
                                          FileStorageService storageService,
                                          PdfProcessingService pdfProcessingService) {
        this.ingestionService = ingestionService;
        this.recordService = recordService;
        this.storageService = storageService;
        this.pdfProcessingService = pdfProcessingService;
    }

    @Override
    public void execute(PdfKnowledgeBaseOperationContext ctx) {
        Map<String, String> fileNamesMappings = storageService.saveFiles(ctx.getInput());
        ctx.getFileNamesMappings().putAll(fileNamesMappings);
        fileNamesMappings.values().forEach(pdfProcessingService::generateThumbnail);
        List<Document> ingested = ingestionService.ingest(ctx);
        recordService.save(ctx, ingested);
    }

    @Override
    public KnowledgeBaseOperationType getType() {
        return KnowledgeBaseOperationType.PDF_UPDATE;
    }
}
