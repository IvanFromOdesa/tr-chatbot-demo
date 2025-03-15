package dev.ivank.trchatbotdemo.common.i18n.translate;

import com.deepl.api.DeepLClient;
import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.TranslatorOptions;
import dev.ivank.trchatbotdemo.common.i18n.Language;

public class ExtendedDeepLClient extends DeepLClient {
    public ExtendedDeepLClient(String authKey, TranslatorOptions options) throws IllegalArgumentException {
        super(authKey, options);
    }

    public Language detectLanguage(String text) throws InterruptedException, DeepLException {
        TextResult result = super.translateText(text, null, "en-US");
        return Language.byAlias(result.getDetectedSourceLanguage());
    }
}
