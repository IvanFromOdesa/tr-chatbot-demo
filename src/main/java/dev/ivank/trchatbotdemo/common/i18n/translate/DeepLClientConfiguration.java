package dev.ivank.trchatbotdemo.common.i18n.translate;

import com.deepl.api.DeepLClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "translation.provider", havingValue = "deepl")
public class DeepLClientConfiguration {
    @Value("${deepl.api.key}")
    private String apiKey;

    @Bean
    public ExtendedDeepLClient deepLClient() {
        return new ExtendedDeepLClient(apiKey, new DeepLClientOptions());
    }
}
