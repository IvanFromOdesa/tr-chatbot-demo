package dev.ivank.trchatbotdemo.kb;

import com.google.common.collect.HashBiMap;
import dev.ivank.trchatbotdemo.kb.domain.PdfEntity;
import dev.ivank.trchatbotdemo.kb.service.pdf.PdfContextOperationRecordService;
import dev.ivank.trchatbotdemo.kb.service.pdf.PdfEntityService;
import dev.ivank.trchatbotdemo.kb.service.pdf.PdfKnowledgeBaseOperationContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfContextOperationRecordServiceTests {
    @Mock
    private PdfEntityService entityService;
    @InjectMocks
    private PdfContextOperationRecordService contextOperationRecordService;
    private Path tempDir;

    @BeforeEach
    void setup() throws IOException {
        tempDir = KbTestUtils.getTempDirectory();

        ReflectionTestUtils.setField(
                contextOperationRecordService,
                "uploadDirPath",
                tempDir.toString()
        );

        ReflectionTestUtils.setField(
                contextOperationRecordService,
                "entityService",
                entityService
        );
    }

    @AfterEach
    void tearDown() {
        KbTestUtils.clearTempDir(tempDir);
    }

    @Test
    void givenValidCtx_shouldSaveRecordingSuccessfully() {
        PdfKnowledgeBaseOperationContext ctx = mock(PdfKnowledgeBaseOperationContext.class);

        String fileName = "file.pdf";

        when(ctx.getFileNamesMappings()).thenReturn(
                HashBiMap.create(Map.of(fileName, "generated.pdf"))
        );

        Map<String, Object> md = new HashMap<>();
        md.put("file_name", fileName);
        md.put("file_size", 12345L);
        md.put("translated", true);

        Document doc = mock(Document.class);
        when(doc.getMetadata()).thenReturn(md);
        when(doc.getId()).thenReturn(UUID.randomUUID().toString());

        List<Document> documents = List.of(doc);

        PdfEntity saved = new PdfEntity();
        saved.setFileName((String) md.get("file_name"));
        saved.setSize((long) md.get("file_size"));
        when(entityService.save(any())).thenReturn(saved);

        contextOperationRecordService.save(ctx, documents);

        @SuppressWarnings("all")
        ArgumentCaptor<List<PdfEntity>> captor = ArgumentCaptor.forClass(List.class);
        verify(entityService).saveAll(captor.capture());

        List<PdfEntity> savedEntities = captor.getValue();

        assertNotNull(savedEntities);
        assertEquals(1, savedEntities.size());

        PdfEntity pdfEntity = savedEntities.get(0);

        assertEquals(fileName, pdfEntity.getFileName());
        assertEquals(12345L, pdfEntity.getSize());
    }
}
