package dev.ivank.trchatbotdemo.kb.domain;

import dev.ivank.trchatbotdemo.security.auth.domain.User;
import dev.ivank.trchatbotdemo.common.Identifier;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners({AuditingEntityListener.class})
public class PdfEntity extends Identifier {
    @CreationTimestamp
    private Instant uploadedAt;
    private String fileName;
    private String originalFileName;
    private long size;
    private String filePath;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @CreatedBy
    private User uploadedBy;
    private boolean translatedToSupported;
    @OneToMany(
            mappedBy = "pdfEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final Set<PdfTextChunkEntity> textChunks = new HashSet<>();

    public Instant getUploadedAt() {
        return uploadedAt;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public boolean isTranslatedToSupported() {
        return translatedToSupported;
    }

    public void setTranslatedToSupported(boolean translatedToSupported) {
        this.translatedToSupported = translatedToSupported;
    }

    public Set<PdfTextChunkEntity> getTextChunks() {
        return textChunks;
    }
}
