package dev.ivank.trchatbotdemo.kb.service.mapping;

import dev.ivank.trchatbotdemo.common.EntityDtoMappingService;
import dev.ivank.trchatbotdemo.common.mapper.MappingContext;
import dev.ivank.trchatbotdemo.kb.domain.PdfEntity;
import dev.ivank.trchatbotdemo.kb.dto.PdfResponseDto;
import org.springframework.stereotype.Service;

@Service
public class PdfResponseDtoMappingService extends EntityDtoMappingService<PdfEntity, PdfResponseDto> {
    public PdfResponseDtoMappingService(MappingContext<PdfEntity, PdfResponseDto> mappingContext) {
        super(mappingContext);
    }
}
