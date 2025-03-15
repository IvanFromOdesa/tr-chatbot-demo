package dev.ivank.trchatbotdemo.chat.dto;

import dev.ivank.trchatbotdemo.report.domain.ReportStatus;

import java.time.Instant;
import java.util.Set;

public class ConversationDto {
    private Instant startedAt;
    private ReportStatus status;
    private Set<ChatInteractionDto> chatInteractions;

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public Set<ChatInteractionDto> getChatInteractions() {
        return chatInteractions;
    }

    public void setChatInteractions(Set<ChatInteractionDto> chatInteractions) {
        this.chatInteractions = chatInteractions;
    }
}
