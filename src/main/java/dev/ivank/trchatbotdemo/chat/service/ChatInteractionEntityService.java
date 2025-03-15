package dev.ivank.trchatbotdemo.chat.service;

import dev.ivank.trchatbotdemo.security.auth.VisitorOnly;
import dev.ivank.trchatbotdemo.chat.domain.ChatInteraction;
import dev.ivank.trchatbotdemo.chat.dao.ChatInteractionDao;
import dev.ivank.trchatbotdemo.common.AbstractEntityService;
import org.springframework.stereotype.Service;

@Service
@VisitorOnly
public class ChatInteractionEntityService extends AbstractEntityService<ChatInteraction, Long, ChatInteractionDao> {
}
