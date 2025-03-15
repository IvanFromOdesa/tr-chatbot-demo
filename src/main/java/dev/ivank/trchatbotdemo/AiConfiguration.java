package dev.ivank.trchatbotdemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Map;

@Configuration
public class AiConfiguration {
    @Value("${spring.application.name}")
    private String applicationName;

    public static final String EMBEDDING_MODEL = "textEmbeddingAdaModel";
    public static final String CHAT_MODEL = "gptChatClient";

    @Bean(EMBEDDING_MODEL)
    @Primary
    public EmbeddingModel textEmbeddingAdaModel(AiConfigurationProperties properties) {
        return new OpenAiEmbeddingModel(
                new OpenAiApi(properties.apiKey()),
                MetadataMode.EMBED,
                OpenAiEmbeddingOptions.builder()
                        .model(properties.getEmbeddingModel())
                        .user(applicationName)
                        .build(),
                RetryUtils.DEFAULT_RETRY_TEMPLATE);
    }

    @Bean(CHAT_MODEL)
    @Primary
    public ChatClient gptChatClient(ChatClient.Builder builder,
                                    MessageChatMemoryAdvisor memoryAdvisor,
                                    AiConfigurationProperties properties) {
        OpenAiChatOptions chatOptions = OpenAiChatOptions
                .builder()
                .model(properties.getChatModel())
                .temperature(properties.chatTemperature())
                .maxTokens(properties.chatTokenLimit())
                .build();

        return builder
                .defaultAdvisors(memoryAdvisor)
                .defaultOptions(chatOptions)
                .build();
    }

    @ConfigurationProperties(prefix = "ai")
    public record AiConfigurationProperties(Map<String, String> models,
                                            String apiKey,
                                            int chatTokenLimit,
                                            double chatTemperature) {
        public String getChatModel() {
            return models.get("chat");
        }

        public String getEmbeddingModel() {
            return models.get("embedding");
        }
    }
}
