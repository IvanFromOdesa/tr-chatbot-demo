package dev.ivank.trchatbotdemo.common.i18n;

import dev.ivank.trchatbotdemo.common.enums.EnumCustomFieldToPersistConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LanguageAliasToPersistConverter extends EnumCustomFieldToPersistConverter<String, Language, String> {
    @Override
    public String convertToDatabaseColumn(Language language) {
        return byValue(language);
    }

    @Override
    public Language convertToEntityAttribute(String s) {
        return byKey(Language.UNDEFINED, s);
    }
}