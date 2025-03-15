package dev.ivank.trchatbotdemo.kb;

import com.google.common.collect.BiMap;
import dev.ivank.trchatbotdemo.common.enums.EnumUtils;
import dev.ivank.trchatbotdemo.common.enums.IEnumConvert;

public enum EmbeddingEntityType implements IEnumConvert<Integer, EmbeddingEntityType> {
    QA(0),
    PDF(1),
    UNDEFINED(CODE_UNDEFINED);

    public static final String EM = "em_type";

    private final int code;

    private static final BiMap<Integer, EmbeddingEntityType> LOOKUP_MAP = EnumUtils.createLookup(EmbeddingEntityType.class);

    EmbeddingEntityType(int code) {
        this.code = code;
    }

    public static EmbeddingEntityType byCode(int code) {
        return LOOKUP_MAP.getOrDefault(code, UNDEFINED);
    }

    public boolean isQA() {
        return this == QA;
    }

    public boolean isPdf() {
        return this == PDF;
    }

    public int getCode() {
        return code;
    }

    @Override
    public BiMap<Integer, EmbeddingEntityType> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public EmbeddingEntityType getDefault() {
        return UNDEFINED;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}
