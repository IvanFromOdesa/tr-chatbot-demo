package dev.ivank.trchatbotdemo.common.form.layout;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.ivank.trchatbotdemo.common.io.CustomSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BSVariantCustomSerializer extends CustomSerializer<BSVariant> {
    @Override
    public Class<BSVariant> getSerializationClass() {
        return BSVariant.class;
    }

    @Override
    public void serialize(BSVariant variant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(variant.getValue());
    }
}
