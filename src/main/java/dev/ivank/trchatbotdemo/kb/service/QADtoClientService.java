package dev.ivank.trchatbotdemo.kb.service;

import dev.ivank.trchatbotdemo.common.AbstractDtoClientService;
import dev.ivank.trchatbotdemo.kb.domain.QAEntity;
import dev.ivank.trchatbotdemo.kb.dto.QAResponseDto;
import dev.ivank.trchatbotdemo.kb.service.qa.QAEntityService;
import org.springframework.stereotype.Service;

@Service
public class QADtoClientService extends AbstractDtoClientService<QAEntity, QAResponseDto, QAEntityService> {
}
