package dev.ivank.trchatbotdemo.kb.domain;

import dev.ivank.trchatbotdemo.common.enums.EnumCustomFieldToPersistConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TranslateStatusCodeToPersistConverter extends EnumCustomFieldToPersistConverter<Integer, TranslateStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TranslateStatus translateStatus) {
        return byValue(translateStatus);
    }

    @Override
    public TranslateStatus convertToEntityAttribute(Integer code) {
        return byKey(TranslateStatus.ORIGINAL, code);
    }
}
