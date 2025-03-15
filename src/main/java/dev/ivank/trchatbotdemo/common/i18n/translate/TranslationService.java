package dev.ivank.trchatbotdemo.common.i18n.translate;

import dev.ivank.trchatbotdemo.common.i18n.Language;

public interface TranslationService {
    String translate(String text, String sourceLang, String targetLang) throws Exception;
    Language detectLanguage(String text) throws Exception;
}
