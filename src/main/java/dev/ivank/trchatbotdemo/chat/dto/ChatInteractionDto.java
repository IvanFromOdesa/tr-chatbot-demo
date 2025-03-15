package dev.ivank.trchatbotdemo.chat.dto;

import dev.ivank.trchatbotdemo.common.i18n.Language;

import java.time.Instant;

public class ChatInteractionDto {
    private String id;
    private String question;
    private Language questionLanguage;
    private String answer;
    private Instant askedAt;
    private double timeToProcess;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}