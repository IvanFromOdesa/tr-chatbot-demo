package dev.ivank.trchatbotdemo.common.i18n;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "i18n")
public record LocalizationProperties(Map<String, String> paths) {

    public String getPath(String key) {
        return this.paths.get(key);
    }

    public Property getProperty(String key) {
        return new Property(getPath(key));
    }

    public record Property(String path) {

    }
}
