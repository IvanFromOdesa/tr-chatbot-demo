package dev.ivank.trchatbotdemo.kb.service;

import dev.ivank.trchatbotdemo.common.AbstractDtoClientService;
import dev.ivank.trchatbotdemo.kb.domain.PdfEntity;
import dev.ivank.trchatbotdemo.kb.dto.PdfResponseDto;
import dev.ivank.trchatbotdemo.kb.service.pdf.PdfEntityService;
import org.springframework.stereotype.Service;

@Service
public class PdfDtoClientService extends AbstractDtoClientService<PdfEntity, PdfResponseDto, PdfEntityService> {
}
