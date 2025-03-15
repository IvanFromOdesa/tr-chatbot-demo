package dev.ivank.trchatbotdemo.security.auth.domain;

import dev.ivank.trchatbotdemo.common.enums.EnumCustomFieldToPersistConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AuthenticationStatusCodeToPersistConverter extends EnumCustomFieldToPersistConverter<Integer, AuthenticationStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AuthenticationStatus authenticationStatus) {
        return byValue(authenticationStatus);
    }

    @Override
    public AuthenticationStatus convertToEntityAttribute(Integer code) {
        return byKey(AuthenticationStatus.CREATED, code);
    }
}
