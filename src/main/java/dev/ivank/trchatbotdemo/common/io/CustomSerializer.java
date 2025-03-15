package dev.ivank.trchatbotdemo.common.io;

import com.fasterxml.jackson.databind.JsonSerializer;

public abstract class CustomSerializer<T> extends JsonSerializer<T> {
    public abstract Class<T> getSerializationClass();
}