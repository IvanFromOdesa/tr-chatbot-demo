package dev.ivank.trchatbotdemo.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "assets")
public record AssetsProperties(Map<String, String> paths) {
    public String getPath(String key) {
        return this.paths.get(key);
    }

    public Property getProperty(String key) {
        return new Property(getPath(key));
    }

    public record Property(String path) {

    }
}
