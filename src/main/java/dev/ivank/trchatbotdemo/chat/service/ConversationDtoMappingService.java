package dev.ivank.trchatbotdemo.chat.service;

import dev.ivank.trchatbotdemo.chat.dto.ConversationDto;
import dev.ivank.trchatbotdemo.common.EntityDtoMappingService;
import dev.ivank.trchatbotdemo.common.mapper.MappingContext;
import dev.ivank.trchatbotdemo.report.domain.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationDtoMappingService extends EntityDtoMappingService<Report, ConversationDto> {
    @Autowired
    public ConversationDtoMappingService(MappingContext<Report, ConversationDto> mappingContext) {
        super(mappingContext);
    }
}
