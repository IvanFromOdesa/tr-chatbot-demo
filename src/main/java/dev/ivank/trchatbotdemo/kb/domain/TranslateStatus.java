package dev.ivank.trchatbotdemo.kb.domain;

import com.google.common.collect.BiMap;
import dev.ivank.trchatbotdemo.common.enums.EnumUtils;
import dev.ivank.trchatbotdemo.common.enums.IEnumConvert;

public enum TranslateStatus implements IEnumConvert<Integer, TranslateStatus> {
    ORIGINAL(0), TRANSLATED(1);

    private final int code;

    private static final BiMap<Integer, TranslateStatus> LOOKUP_MAP = EnumUtils.createLookup(TranslateStatus.class);

    TranslateStatus(int code) {
        this.code = code;
    }

    @Override
    public BiMap<Integer, TranslateStatus> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public TranslateStatus getDefault() {
        return ORIGINAL;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}
