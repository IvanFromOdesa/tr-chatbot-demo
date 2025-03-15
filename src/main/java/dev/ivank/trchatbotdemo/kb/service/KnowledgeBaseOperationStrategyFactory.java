package dev.ivank.trchatbotdemo.kb.service;

import dev.ivank.trchatbotdemo.kb.ctx.KnowledgeBaseOperationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class KnowledgeBaseOperationStrategyFactory {
    private final Map<KnowledgeBaseOperationType, KnowledgeBaseOperationStrategy<?, ?>> strategies;

    @Autowired
    public KnowledgeBaseOperationStrategyFactory(List<KnowledgeBaseOperationStrategy<?, ?>> strategies) {
        this.strategies = strategies.stream().collect(Collectors.toMap(KnowledgeBaseOperationStrategy::getType, Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public <S, C extends KnowledgeBaseOperationContext<S>> KnowledgeBaseOperationStrategy<S, C> getStrategy(KnowledgeBaseOperationType type) {
        return (KnowledgeBaseOperationStrategy<S, C>) strategies.get(type);
    }
}
