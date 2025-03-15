package dev.ivank.trchatbotdemo.common.i18n;

import org.springframework.context.MessageSourceAware;

import java.util.Locale;
import java.util.Map;

public interface MessageSourceResolver extends MessageSourceAware {
    Map<String, String> getMessages(Locale locale);
    String getMessage(String key, Locale locale, Object... args);
}
