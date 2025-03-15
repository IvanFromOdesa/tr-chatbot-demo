package dev.ivank.trchatbotdemo.common.valid;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.ivank.trchatbotdemo.common.io.CustomSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ErrorMapSerializer extends CustomSerializer<ErrorMap> {
    @Override
    public void serialize(ErrorMap value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        for (Map.Entry<String, ErrorMap.Error> entry : value.getErrors().entrySet()) {
            gen.writeFieldName(entry.getKey());
            serializers.defaultSerializeValue(entry.getValue(), gen);
        }
        gen.writeEndObject();
    }

    @Override
    public Class<ErrorMap> getSerializationClass() {
        return ErrorMap.class;
    }
}
