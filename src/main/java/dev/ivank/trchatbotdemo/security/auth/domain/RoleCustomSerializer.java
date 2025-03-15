package dev.ivank.trchatbotdemo.security.auth.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.ivank.trchatbotdemo.common.io.CustomSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RoleCustomSerializer extends CustomSerializer<Role> {
    @Override
    public Class<Role> getSerializationClass() {
        return Role.class;
    }

    @Override
    public void serialize(Role role, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("code", role.getCode());
        jsonGenerator.writeStringField("alias", role.getAlias());
        jsonGenerator.writeEndObject();
    }
}
