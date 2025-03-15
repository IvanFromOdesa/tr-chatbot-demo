package dev.ivank.trchatbotdemo.kb.dto;

import dev.ivank.trchatbotdemo.common.form.UserDataDto;

import java.time.Instant;

public class PdfResponseDto {
    private Long id;
    private Instant uploadedAt;
    private String fileName;
    private String originalFileName;
    private long size;
    private boolean translatedToSupported;
    private UserDataDto uploadedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isTranslatedToSupported() {
        return translatedToSupported;
    }

    public void setTranslatedToSupported(boolean translatedToSupported) {
        this.translatedToSupported = translatedToSupported;
    }

    public UserDataDto getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(UserDataDto uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
}
