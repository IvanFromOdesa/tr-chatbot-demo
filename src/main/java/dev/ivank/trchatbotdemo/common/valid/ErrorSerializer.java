package dev.ivank.trchatbotdemo.common.valid;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.ivank.trchatbotdemo.common.io.CustomSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Component
public class ErrorSerializer extends CustomSerializer<ErrorMap.Error> {
    @Override
    public void serialize(ErrorMap.Error value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Map<String, String> errors = value.getErrors();
        if (errors != null) {
            gen.writeObject(errors);
        } else if (value.getMsg() != null) {
            gen.writeString(value.getMsg());
            Serializable input = value.getInput();
            if (input != null) {
                if (input instanceof String) {
                    gen.writeStringField("input", (String) input);
                } else if (input instanceof Number) {
                    gen.writeNumberField("input", (long) input);
                } else if (input instanceof Boolean) {
                    gen.writeBooleanField("input", (boolean) input);
                } else {
                    gen.writeObjectField("input", input);
                }
            }
        } else {
            gen.writeNull();
        }
    }

    @Override
    public Class<ErrorMap.Error> getSerializationClass() {
        return ErrorMap.Error.class;
    }
}
