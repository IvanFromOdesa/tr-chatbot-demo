package dev.ivank.trchatbotdemo.kb;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "validation.qa")
public class QaValidationProperties {
    private final int questionMaxSize;
    private final int answerMaxSize;

    public QaValidationProperties(int questionMaxSize, int answerMaxSize) {
        this.questionMaxSize = questionMaxSize;
        this.answerMaxSize = answerMaxSize;
    }

    public int getQuestionMaxSize() {
        return questionMaxSize;
    }

    public int getAnswerMaxSize() {
        return answerMaxSize;
    }
}
