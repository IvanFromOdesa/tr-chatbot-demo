package dev.ivank.trchatbotdemo.common.i18n.translate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@ConditionalOnProperty(name = "translation.provider", havingValue = "libre")
public class LibreTranslateConfiguration {
    @Value("${libre.api.host}")
    private String host;

    public static final String REST_TEMPLATE = "libreRestTemplate";

    @Bean(REST_TEMPLATE)
    public RestTemplate libreRestTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri(host)
                .requestFactory(this::clientHttpRequestFactory)
                .build();
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Math.toIntExact(Duration.ofSeconds(15).toMillis()));
        factory.setReadTimeout(Math.toIntExact(Duration.ofSeconds(30).toMillis()));
        return factory;
    }
}
