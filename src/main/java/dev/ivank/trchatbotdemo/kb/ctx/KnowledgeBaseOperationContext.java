package dev.ivank.trchatbotdemo.kb.ctx;

import java.util.List;

public class KnowledgeBaseOperationContext<S> {
    private List<S> input;
    private boolean translateToSupported;

    public KnowledgeBaseOperationContext(List<S> input) {
        this.input = input;
    }

    public KnowledgeBaseOperationContext(List<S> input, boolean translateToSupported) {
        this.input = input;
        this.translateToSupported = translateToSupported;
    }

    public List<S> getInput() {
        return input;
    }

    public void setInput(List<S> input) {
        this.input = input;
    }

    public boolean isTranslateToSupported() {
        return translateToSupported;
    }

    public void setTranslateToSupported(boolean translateToSupported) {
        this.translateToSupported = translateToSupported;
    }
}
