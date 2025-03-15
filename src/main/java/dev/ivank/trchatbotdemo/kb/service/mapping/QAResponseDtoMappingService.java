package dev.ivank.trchatbotdemo.kb.service.mapping;

import dev.ivank.trchatbotdemo.common.EntityDtoMappingService;
import dev.ivank.trchatbotdemo.common.mapper.MappingContext;
import dev.ivank.trchatbotdemo.kb.domain.QAEntity;
import dev.ivank.trchatbotdemo.kb.dto.QAResponseDto;
import org.springframework.stereotype.Service;

@Service
public class QAResponseDtoMappingService extends EntityDtoMappingService<QAEntity, QAResponseDto> {
    public QAResponseDtoMappingService(MappingContext<QAEntity, QAResponseDto> mappingContext) {
        super(mappingContext);
    }
}
