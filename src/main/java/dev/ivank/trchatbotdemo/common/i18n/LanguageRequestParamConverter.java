package dev.ivank.trchatbotdemo.common.i18n;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LanguageRequestParamConverter implements Converter<String, Language> {
    @Override
    public Language convert(String source) {
        Language byAlias = Language.byAlias(source);
        return byAlias.isValid() ? byAlias : Language.DEFAULT;
    }
}
