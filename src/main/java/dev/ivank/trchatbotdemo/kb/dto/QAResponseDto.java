package dev.ivank.trchatbotdemo.kb.dto;

import dev.ivank.trchatbotdemo.common.form.UserDataDto;
import dev.ivank.trchatbotdemo.common.i18n.Language;

import java.time.Instant;

public class QAResponseDto extends QABaseDto {
    private Instant uploadedAt;
    private Language language;
    private int translateStatus;
    private UserDataDto uploadedBy;
    private Long originalQdbId;

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getTranslateStatus() {
        return translateStatus;
    }

    public void setTranslateStatus(int translateStatus) {
        this.translateStatus = translateStatus;
    }

    public UserDataDto getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(UserDataDto uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Long getOriginalQdbId() {
        return originalQdbId;
    }

    public void setOriginalQdbId(Long originalQdbId) {
        this.originalQdbId = originalQdbId;
    }
}
