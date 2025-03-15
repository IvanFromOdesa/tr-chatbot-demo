package dev.ivank.trchatbotdemo;

import dev.ivank.trchatbotdemo.common.AssetsProperties;
import dev.ivank.trchatbotdemo.common.ControllerProperties;
import dev.ivank.trchatbotdemo.common.i18n.LocalizationProperties;
import dev.ivank.trchatbotdemo.kb.QaValidationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties({
        ControllerProperties.class,
        LocalizationProperties.class,
        AssetsProperties.class,
        AiConfiguration.AiConfigurationProperties.class,
        QaValidationProperties.class
})
@EnableJpaAuditing
public class TrChatbotDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrChatbotDemoApplication.class, args);
    }
}
