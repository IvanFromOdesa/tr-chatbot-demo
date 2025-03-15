package dev.ivank.trchatbotdemo.common.rag;

import org.springframework.ai.document.Document;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public final class RagUtils {
    public static Map<String, Object> getFilteredOgDocMetadata(Document ogDoc, String... mdKeys) {
        return getFilteredOgDocMetadata(ogDoc.getMetadata(), mdKeys);
    }

    public static Map<String, Object> getFilteredOgDocMetadata(Map<String, Object> md, String... mdKeys) {
        return md
                .entrySet()
                .stream()
                .filter(e -> Arrays.stream(mdKeys).noneMatch(key -> key.equals(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
