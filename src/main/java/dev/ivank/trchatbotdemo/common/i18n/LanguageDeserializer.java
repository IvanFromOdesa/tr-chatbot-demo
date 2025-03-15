package dev.ivank.trchatbotdemo.common.i18n;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import dev.ivank.trchatbotdemo.common.io.CustomDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LanguageDeserializer extends CustomDeserializer<Language> {
    @Override
    public Language deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);
        String alias = rootNode.get("alias").asText();
        Language byAlias = Language.byAlias(alias);
        return byAlias.isValid() ? byAlias : Language.DEFAULT;
    }

    @Override
    public Class<Language> getDeserializationClass() {
        return Language.class;
    }
}