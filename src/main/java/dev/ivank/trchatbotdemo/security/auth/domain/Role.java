package dev.ivank.trchatbotdemo.security.auth.domain;

import com.google.common.collect.BiMap;
import dev.ivank.trchatbotdemo.common.enums.EnumUtils;
import dev.ivank.trchatbotdemo.common.enums.IEnumConvert;

public enum Role implements IEnumConvert<Integer, Role> {
    VISITOR(0), EMPLOYEE(1), ANONYMOUS(CODE_UNDEFINED);

    private final int code;
    private final String alias;

    public static final String ROLE_PREFIX = "ROLE_";

    private static final BiMap<Integer, Role> LOOKUP_MAP = EnumUtils.createLookup(Role.class);

    Role(int code) {
        this.code = code;
        this.alias = this.name().toLowerCase();
    }

    public int getCode() {
        return code;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isVisitor() {
        return this == VISITOR;
    }

    public boolean isEmployee() {
        return this == EMPLOYEE;
    }

    @Override
    public BiMap<Integer, Role> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public Role getDefault() {
        return ANONYMOUS;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}
