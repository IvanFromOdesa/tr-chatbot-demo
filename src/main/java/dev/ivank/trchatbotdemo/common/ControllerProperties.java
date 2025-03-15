package dev.ivank.trchatbotdemo.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "controller")
public record ControllerProperties(Map<String, String> pages, Map<String, String> paths) {

    public String getPage(String key) {
        return this.pages.get(key);
    }

    public String getPath(String key) {
        return this.paths.get(key);
    }

    public Property getProperty(String key) {
        return new Property(getPage(key), getPath(key));
    }

    public record Property(String page, String path) {

    }
}