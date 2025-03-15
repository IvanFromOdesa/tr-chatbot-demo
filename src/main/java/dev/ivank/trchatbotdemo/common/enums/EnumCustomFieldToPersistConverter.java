package dev.ivank.trchatbotdemo.common.enums;

import jakarta.persistence.AttributeConverter;

public abstract class EnumCustomFieldToPersistConverter<K, E extends Enum<E> & IEnumConvert<K, E>, TYPE> implements AttributeConverter<E, TYPE> {
    protected E byKey(E e, K k) {
        return e.getByKey(k);
    }

    protected K byValue(E e) {
        return e.getKey();
    }
}