package dev.ivank.trchatbotdemo.kb.dto;

import java.util.List;

public class QAsDto {
    private List<QAUpdateDto> qas;
    private boolean translateToSupported;

    public List<QAUpdateDto> getQas() {
        return qas;
    }

    public void setQas(List<QAUpdateDto> qas) {
        this.qas = qas;
    }

    public boolean isTranslateToSupported() {
        return translateToSupported;
    }

    public void setTranslateToSupported(boolean translateToSupported) {
        this.translateToSupported = translateToSupported;
    }
}
