package dev.ivank.trchatbotdemo.common.enums;

import com.google.common.collect.BiMap;

public interface IEnumConvert<K, E extends Enum<E>> {
    int CODE_UNDEFINED = -1;
    String CODE_UNDEFINED_STR = String.valueOf(CODE_UNDEFINED);

    BiMap<K, E> getLookup();

    default E getByKey(K key) {
        E e = getLookup().get(key);
        return e == null ? getDefault() : e;
    }

    E getDefault();

    K getKey();
}