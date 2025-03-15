package dev.ivank.trchatbotdemo.chat.domain;

import com.google.common.base.Objects;
import dev.ivank.trchatbotdemo.common.Identifier;
import dev.ivank.trchatbotdemo.common.i18n.Language;
import dev.ivank.trchatbotdemo.report.domain.Report;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity
public class ChatInteraction extends Identifier implements Comparable<ChatInteraction> {
    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String question;
    private Language questionLanguage;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String answer;
    private Instant askedAt;
    private double timeToProcess;
    private boolean noContext;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Language getQuestionLanguage() {
        return questionLanguage;
    }

    public void setQuestionLanguage(Language questionLanguage) {
        this.questionLanguage = questionLanguage;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Instant getAskedAt() {
        return askedAt;
    }

    public void setAskedAt(Instant askedAt) {
        this.askedAt = askedAt;
    }

    public double getTimeToProcess() {
        return timeToProcess;
    }

    public void setTimeToProcess(double timeToProcess) {
        this.timeToProcess = timeToProcess;
    }

    public boolean isNoContext() {
        return noContext;
    }

    public void setNoContext(boolean noContext) {
        this.noContext = noContext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatInteraction that = (ChatInteraction) o;
        return Objects.equal(report, that.report) && Objects.equal(askedAt, that.askedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(report, askedAt);
    }

    @Override
    public int compareTo(ChatInteraction o) {
        return this.getAskedAt().compareTo(o.getAskedAt());
    }
}
