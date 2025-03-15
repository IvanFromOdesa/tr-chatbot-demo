package dev.ivank.trchatbotdemo.common.i18n.translate;

import com.deepl.api.DeepLException;
import dev.ivank.trchatbotdemo.common.i18n.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "translation.provider", havingValue = "deepl")
public class DeepLTranslationService implements TranslationService {
    private final ExtendedDeepLClient translationService;

    @Autowired
    public DeepLTranslationService(ExtendedDeepLClient translationService) {
        this.translationService = translationService;
    }

    @Override
    public String translate(String text, String sourceLang, String targetLang) throws DeepLException, InterruptedException {
        return translationService
                // This is deepL 'questionable' api that accepts en-US as target lang (but not en!)
                // and en as a sourceLang (but never en-US)
                .translateText(text, sourceLang.equals("en-US") ? "en" : sourceLang, targetLang)
                .getText();
    }

    @Override
    public Language detectLanguage(String text) throws DeepLException, InterruptedException {
        Language language = translationService.detectLanguage(text);
        return language ==  Language.DEFAULT ? Language.ENGLISH : language;
    }
}
