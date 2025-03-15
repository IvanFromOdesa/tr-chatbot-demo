package dev.ivank.trchatbotdemo.kb.service;

import dev.ivank.trchatbotdemo.security.auth.EmployeeOnly;
import dev.ivank.trchatbotdemo.kb.ctx.KnowledgeBaseOperationContext;

public interface KnowledgeBaseOperationStrategy<S, CONTEXT extends KnowledgeBaseOperationContext<S>> {
    @EmployeeOnly
    void execute(CONTEXT ctx);
    KnowledgeBaseOperationType getType();
}
