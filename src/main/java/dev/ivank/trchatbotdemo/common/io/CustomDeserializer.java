package dev.ivank.trchatbotdemo.common.io;

import com.fasterxml.jackson.databind.JsonDeserializer;

public abstract class CustomDeserializer<T> extends JsonDeserializer<T> {
    public abstract Class<T> getDeserializationClass();
}