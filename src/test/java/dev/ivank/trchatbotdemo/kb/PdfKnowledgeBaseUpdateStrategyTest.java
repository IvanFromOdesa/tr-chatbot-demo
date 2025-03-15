package dev.ivank.trchatbotdemo.kb;

import dev.ivank.trchatbotdemo.common.io.FileStorageService;
import dev.ivank.trchatbotdemo.common.io.PdfProcessingService;
import dev.ivank.trchatbotdemo.kb.service.pdf.PdfContextOperationRecordService;
import dev.ivank.trchatbotdemo.kb.service.pdf.PdfKnowledgeBaseOperationContext;
import dev.ivank.trchatbotdemo.kb.service.pdf.PdfKnowledgeBaseUpdateIngestionService;
import dev.ivank.trchatbotdemo.kb.service.pdf.PdfKnowledgeBaseUpdateStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfKnowledgeBaseUpdateStrategyTest {
    @Mock
    private PdfKnowledgeBaseUpdateIngestionService ingestionService;
    @Mock
    private PdfContextOperationRecordService recordService;
    @Mock
    private FileStorageService storageService;
    @Mock
    private PdfProcessingService pdfProcessingService;
    @InjectMocks
    private PdfKnowledgeBaseUpdateStrategy strategy;

    @Test
    void shouldCallDependencies() {
        PdfKnowledgeBaseOperationContext ctx = new PdfKnowledgeBaseOperationContext(List.of());
        Map<String, String> fileMappings = new HashMap<>();
        fileMappings.put("original.pdf", "saved/file.pdf");

        when(storageService.saveFiles(any())).thenReturn(fileMappings);

        strategy.execute(ctx);

        verify(storageService).saveFiles(any());
        verify(pdfProcessingService).generateThumbnail("saved/file.pdf");
        verify(ingestionService).ingest(ctx);
        verify(recordService).save(same(ctx), any());
    }

    @Test
    void shouldCallWithEmptyInput() {
        PdfKnowledgeBaseOperationContext ctx = new PdfKnowledgeBaseOperationContext(List.of());

        strategy.execute(ctx);

        verify(storageService).saveFiles(any());
        verify(pdfProcessingService, never()).generateThumbnail(anyString());
        verify(ingestionService).ingest(ctx);
        verify(recordService).save(ctx, List.of());
    }
}
