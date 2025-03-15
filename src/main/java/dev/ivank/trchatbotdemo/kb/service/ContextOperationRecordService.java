package dev.ivank.trchatbotdemo.kb.service;

import dev.ivank.trchatbotdemo.common.AbstractEntityService;
import dev.ivank.trchatbotdemo.common.IdAware;
import dev.ivank.trchatbotdemo.security.auth.EmployeeOnly;
import dev.ivank.trchatbotdemo.kb.ctx.KnowledgeBaseOperationContext;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EmployeeOnly
public abstract class ContextOperationRecordService<S, CONTEXT extends KnowledgeBaseOperationContext<S>, E extends IdAware<?>, ES extends AbstractEntityService<E, ?, ?>> {
    @Autowired
    protected ES entityService;

    @Transactional
    public void save(CONTEXT ctx, List<Document> storedEmbeddings) {
        List<E> entities = this.transformToDomain(ctx, storedEmbeddings);
        entityService.saveAll(entities);
    }

    protected abstract List<E> transformToDomain(CONTEXT ctx, List<Document> storedEmbeddings);
}
