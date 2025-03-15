package dev.ivank.trchatbotdemo.kb.domain;

import dev.ivank.trchatbotdemo.security.auth.domain.User;
import dev.ivank.trchatbotdemo.common.Identifier;
import dev.ivank.trchatbotdemo.common.i18n.Language;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(name = "uk_vectorstoreid", columnList = "vectorStoreId", unique = true)
})
@EntityListeners({AuditingEntityListener.class})
public class QAEntity extends Identifier {
    @CreationTimestamp
    private Instant uploadedAt;
    @Column(nullable = false)
    private UUID vectorStoreId;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String question;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String answer;
    private Language language;
    private TranslateStatus translateStatus;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @CreatedBy
    private User uploadedBy;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "original_qa", referencedColumnName = "id")
    private QAEntity originalQA;

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public UUID getVectorStoreId() {
        return vectorStoreId;
    }

    public void setVectorStoreId(UUID vectorStoreId) {
        this.vectorStoreId = vectorStoreId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public TranslateStatus getTranslateStatus() {
        return translateStatus;
    }

    public void setTranslateStatus(TranslateStatus translateStatus) {
        this.translateStatus = translateStatus;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public QAEntity getOriginalQA() {
        return originalQA;
    }

    public void setOriginalQA(QAEntity originalQA) {
        this.originalQA = originalQA;
    }
}
