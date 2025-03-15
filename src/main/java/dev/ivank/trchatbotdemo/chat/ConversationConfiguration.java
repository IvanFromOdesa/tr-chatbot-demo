package dev.ivank.trchatbotdemo.chat;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class ConversationConfiguration {
    @Bean
    @SessionScope
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public MessageChatMemoryAdvisor messageChatMemoryAdvisor() {
        return new MessageChatMemoryAdvisor(new InMemoryChatMemory());
    }
}
