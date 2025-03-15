package dev.ivank.trchatbotdemo.common.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class IOConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper(List<CustomSerializer<?>> serializers, List<CustomDeserializer<?>> deserializers) {
        ObjectMapper mapper = getDefault();
        SimpleModule module = new SimpleModule();
        addSerializers(serializers, module);
        addDeserializers(deserializers, module);
        mapper.registerModule(module);
        return mapper;
    }

    public static void addDeserializers(List<CustomDeserializer<?>> deserializers, SimpleModule module) {
        for (CustomDeserializer<?> deserializer: deserializers) {
            addDeserializerToModule(module, deserializer);
        }
    }

    public static void addSerializers(List<CustomSerializer<?>> serializers, SimpleModule module) {
        for (CustomSerializer<?> serializer: serializers) {
            addSerializerToModule(module, serializer);
        }
    }

    private static <T> void addSerializerToModule(SimpleModule module, CustomSerializer<T> serializer) {
        module.addSerializer(serializer.getSerializationClass(), serializer);
    }

    private static <T> void addDeserializerToModule(SimpleModule module, CustomDeserializer<T> deserializer) {
        module.addDeserializer(deserializer.getDeserializationClass(), deserializer);
    }

    public static ObjectMapper getDefault() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        objectMapper.registerModule(module);
        objectMapper.registerModule(new GuavaModule());
        return objectMapper;
    }
}