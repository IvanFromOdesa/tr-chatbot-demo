package dev.ivank.trchatbotdemo.chat.dao;

import dev.ivank.trchatbotdemo.chat.domain.ChatInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatInteractionDao extends JpaRepository<ChatInteraction, Long> {
}
