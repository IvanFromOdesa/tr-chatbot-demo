package dev.ivank.trchatbotdemo.security.auth.domain;

import dev.ivank.trchatbotdemo.common.enums.EnumCustomFieldToPersistConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleCodeToPersistConverter extends EnumCustomFieldToPersistConverter<Integer, Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return byValue(role);
    }

    @Override
    public Role convertToEntityAttribute(Integer code) {
        return byKey(Role.VISITOR, code);
    }
}
