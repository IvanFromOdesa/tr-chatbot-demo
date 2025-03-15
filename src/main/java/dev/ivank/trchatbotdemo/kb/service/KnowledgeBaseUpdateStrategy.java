package dev.ivank.trchatbotdemo.kb.service;

import dev.ivank.trchatbotdemo.kb.ctx.KnowledgeBaseOperationContext;

public interface KnowledgeBaseUpdateStrategy<S, CONTEXT extends KnowledgeBaseOperationContext<S>>
        extends KnowledgeBaseOperationStrategy<S, CONTEXT> {
}
