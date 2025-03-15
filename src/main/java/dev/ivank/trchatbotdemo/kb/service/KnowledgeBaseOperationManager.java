package dev.ivank.trchatbotdemo.kb.service;

import dev.ivank.trchatbotdemo.security.auth.EmployeeOnly;
import dev.ivank.trchatbotdemo.kb.ctx.KnowledgeBaseOperationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@EmployeeOnly
public class KnowledgeBaseOperationManager {
    private final KnowledgeBaseOperationStrategyFactory strategyFactory;

    @Autowired
    public KnowledgeBaseOperationManager(KnowledgeBaseOperationStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Async("delegatingSecurityContextExecutor")
    public <S, CONTEXT extends KnowledgeBaseOperationContext<S>> void execute(KnowledgeBaseOperationType type, CONTEXT ctx) {
        KnowledgeBaseOperationStrategy<S, CONTEXT> strategy = strategyFactory.getStrategy(type);
        if (strategy == null) {
            throw new IllegalArgumentException("No operation strategy found for type: %s".formatted(type.name()));
        }
        strategy.execute(ctx);
    }
}
