package dev.ivank.trchatbotdemo.kb.service.qa;

import dev.ivank.trchatbotdemo.kb.ctx.KnowledgeBaseOperationContext;
import dev.ivank.trchatbotdemo.kb.dto.QAUpdateDto;
import dev.ivank.trchatbotdemo.kb.service.KnowledgeBaseOperationType;
import dev.ivank.trchatbotdemo.kb.service.KnowledgeBaseUpdateStrategy;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QAKnowledgeBaseUpdateStrategy implements KnowledgeBaseUpdateStrategy<QAUpdateDto, KnowledgeBaseOperationContext<QAUpdateDto>> {
    private final QAKnowledgeBaseUpdateIngestionService ingestionService;
    private final QAContextOperationRecordService recordService;

    @Autowired
    public QAKnowledgeBaseUpdateStrategy(QAKnowledgeBaseUpdateIngestionService ingestionService,
                                         QAContextOperationRecordService recordService) {
        this.ingestionService = ingestionService;
        this.recordService = recordService;
    }

    @Override
    public void execute(KnowledgeBaseOperationContext<QAUpdateDto> ctx) {
        List<Document> ingested = ingestionService.ingest(ctx);
        recordService.save(ctx, ingested);
    }

    @Override
    public KnowledgeBaseOperationType getType() {
        return KnowledgeBaseOperationType.QA_UPDATE;
    }
}
