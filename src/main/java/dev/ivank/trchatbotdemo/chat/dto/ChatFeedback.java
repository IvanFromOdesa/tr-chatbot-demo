package dev.ivank.trchatbotdemo.chat.dto;

import java.util.Objects;

public enum ChatFeedback {
    POSITIVE("0"),
    NEGATIVE("1");

    private final String code;

    ChatFeedback(String code) {
        this.code = code;
    }

    public boolean isPositive() {
        return this == POSITIVE;
    }

    public boolean isNegative() {
        return this == NEGATIVE;
    }

    public static ChatFeedback byCode(String code) {
        for (ChatFeedback chatFeedback: ChatFeedback.values()) {
            if (Objects.equals(chatFeedback.getCode(), code)) {
                return chatFeedback;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }
}
