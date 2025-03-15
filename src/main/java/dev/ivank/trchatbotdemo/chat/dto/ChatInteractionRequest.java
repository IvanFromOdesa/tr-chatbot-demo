package dev.ivank.trchatbotdemo.chat.dto;

import dev.ivank.trchatbotdemo.common.i18n.Language;
import dev.ivank.trchatbotdemo.report.domain.Report;

import java.time.Instant;
import java.util.Objects;

public final class ChatInteractionRequest {
    private volatile Instant askedAt;
    private volatile Report currentReport;
    private volatile String userPrompt;
    private volatile Language userLanguage;
    private volatile boolean noContext;
    private volatile double timeToProcess;
    private volatile String response;

    public Instant getAskedAt() {
        return askedAt;
    }

    public void setAskedAt(Instant askedAt) {
        this.askedAt = askedAt;
    }

    public Report getCurrentReport() {
        return currentReport;
    }

    public void setCurrentReport(Report currentReport) {
        this.currentReport = currentReport;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }

    public Language getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(Language userLanguage) {
        this.userLanguage = userLanguage;
    }

    public boolean isNoContext() {
        return noContext;
    }

    public void setNoContext(boolean noContext) {
        this.noContext = noContext;
    }

    public double getTimeToProcess() {
        return timeToProcess;
    }

    public void setTimeToProcess(double timeToProcess) {
        this.timeToProcess = timeToProcess;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
