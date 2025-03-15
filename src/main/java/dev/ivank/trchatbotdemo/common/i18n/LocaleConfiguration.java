package dev.ivank.trchatbotdemo.common.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfiguration {
    @Value("${application.locale}")
    private String defaultLocaleCode;
    private final LocalizationProperties properties;

    @Autowired
    public LocaleConfiguration(LocalizationProperties properties) {
        this.properties = properties;
    }

    @Bean("homeBundles")
    @Scope("prototype")
    public MessageSource homeBundles() {
        return getRB(properties.getPath("index"));
    }

    @Bean("loginBundles")
    @Scope("prototype")
    public MessageSource loginBundles() {
        return getRB(properties.getPath("login"));
    }

    @Bean("signupBundles")
    @Scope("prototype")
    public MessageSource signupBundles() {
        return getRB(properties.getPath("signup"));
    }

    @Bean("reportBundles")
    @Scope("prototype")
    public MessageSource reportBundles() {
        return getRB(properties.getPath("report"));
    }

    @Bean("knowledgeBaseBundles")
    @Scope("prototype")
    public MessageSource knowledgeBaseBundles() {
        return getRB(properties.getPath("knowledgeBase"));
    }

    @Bean("chatBundles")
    @Scope("prototype")
    public MessageSource chatBundles() {
        return getRB(properties.getPath("chat"));
    }

    @Bean("layoutBundles")
    @Scope("prototype")
    public MessageSource layoutBundles() {
        return getRB(properties.getPath("layout"));
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale(defaultLocaleCode));
        return localeResolver;
    }

    public static ExposedResourceBundleMessageSource getRB(String path) {
        ExposedResourceBundleMessageSource messageSource = new ExposedResourceBundleMessageSource();
        messageSource.setBasename(String.format("classpath*:/bundle/%s/*", path));
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(10);
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}