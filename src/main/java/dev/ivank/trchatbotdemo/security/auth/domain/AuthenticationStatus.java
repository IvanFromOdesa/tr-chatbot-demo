package dev.ivank.trchatbotdemo.security.auth.domain;

import com.google.common.collect.BiMap;
import dev.ivank.trchatbotdemo.common.enums.EnumUtils;
import dev.ivank.trchatbotdemo.common.enums.IEnumConvert;

public enum AuthenticationStatus implements IEnumConvert<Integer, AuthenticationStatus> {
    CREATED(0), CONFIRMED(1);

    private final int code;

    private static final BiMap<Integer, AuthenticationStatus> LOOKUP_MAP = EnumUtils.createLookup(AuthenticationStatus.class);

    AuthenticationStatus(int code) {
        this.code = code;
    }

    @Override
    public BiMap<Integer, AuthenticationStatus> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public AuthenticationStatus getDefault() {
        return CREATED;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}