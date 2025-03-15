package dev.ivank.trchatbotdemo.chat.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChatFeedbackRequestParamConverter implements Converter<String, ChatFeedback> {
    @Override
    public ChatFeedback convert(String source) {
        return ChatFeedback.byCode(source);
    }
}
