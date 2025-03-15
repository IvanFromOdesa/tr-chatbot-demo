package dev.ivank.trchatbotdemo.common;

import java.io.Serializable;

public class EntityNotFoundException extends RuntimeException {
    private final Serializable id;

    public EntityNotFoundException(String code, Serializable id) {
        super(code);
        this.id = id;
    }

    public Serializable getId() {
        return id;
    }
}