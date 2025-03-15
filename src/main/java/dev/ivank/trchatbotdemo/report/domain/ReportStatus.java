package dev.ivank.trchatbotdemo.report.domain;

import com.google.common.collect.BiMap;
import dev.ivank.trchatbotdemo.common.enums.EnumUtils;
import dev.ivank.trchatbotdemo.common.enums.IEnumConvert;

public enum ReportStatus implements IEnumConvert<Integer, ReportStatus> {
    INITIALIZED(0),
    NOT_YET_COMPLETED(1),
    COMPLETED_EXPLICITLY(2),
    COMPLETED_IMPLICITLY(3);

    private final int code;
    private final String alias;

    private static final BiMap<Integer, ReportStatus> LOOKUP_MAP = EnumUtils.createLookup(ReportStatus.class);

    ReportStatus(int code) {
        this.code = code;
        this.alias = this.name().toLowerCase();
    }

    public boolean isInitialized() {
        return this == INITIALIZED;
    }

    public boolean isCompleted() {
        return this == COMPLETED_EXPLICITLY;
    }

    public int getCode() {
        return code;
    }

    public String getAlias() {
        return alias;
    }

    public static ReportStatus byCode(int code) {
        return LOOKUP_MAP.get(code);
    }

    @Override
    public BiMap<Integer, ReportStatus> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public ReportStatus getDefault() {
        return INITIALIZED;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}
