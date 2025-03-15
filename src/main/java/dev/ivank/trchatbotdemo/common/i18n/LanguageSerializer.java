package dev.ivank.trchatbotdemo.common.i18n;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.ivank.trchatbotdemo.common.io.CustomSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LanguageSerializer extends CustomSerializer<Language> {
    @Override
    public void serialize(Language language, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("alias", language.getAlias());
        gen.writeStringField("name", language.getName());
        gen.writeStringField("helpText", language.getHelpText());
        gen.writeStringField("imagePath", language.getImagePath());
        gen.writeEndObject();
    }

    @Override
    public Class<Language> getSerializationClass() {
        return Language.class;
    }
}