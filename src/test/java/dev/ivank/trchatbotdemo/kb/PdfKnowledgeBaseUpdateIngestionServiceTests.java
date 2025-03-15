package dev.ivank.trchatbotdemo.kb;

import com.google.common.collect.HashBiMap;
import dev.ivank.trchatbotdemo.common.i18n.Language;
import dev.ivank.trchatbotdemo.common.i18n.translate.TranslationService;
import dev.ivank.trchatbotdemo.kb.service.pdf.PdfKnowledgeBaseOperationContext;
import dev.ivank.trchatbotdemo.kb.service.pdf.PdfKnowledgeBaseUpdateIngestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfKnowledgeBaseUpdateIngestionServiceTests {
    @Mock
    private VectorStore vectorStore;
    @Mock
    private TranslationService translationService;
    @InjectMocks
    private PdfKnowledgeBaseUpdateIngestionService ingestionService;
    private Path tempDir;
    private Path pdfFile;

    @BeforeEach
    void setup() throws IOException {
        tempDir = KbTestUtils.getTempDirectory();
        pdfFile = KbTestUtils.copyTestPdfToTempDir(tempDir.resolve("test.pdf"));
        ReflectionTestUtils.setField(
                ingestionService,
                "uploadDirPath",
                tempDir.toString()
        );
    }

    @AfterEach
    void tearDown() {
        KbTestUtils.clearTempDir(tempDir);
    }

    @Test
    void givenValidPdf_shouldIngestSuccessfully() throws Exception {
        when(translationService.detectLanguage(anyString())).thenReturn(Language.ENGLISH);
        when(translationService.translate(anyString(), anyString(), anyString())).thenReturn("Translated text");

        PdfKnowledgeBaseOperationContext ctx = mock(PdfKnowledgeBaseOperationContext.class);
        when(ctx.getFileNamesMappings()).thenReturn(HashBiMap.create(Map.of("test.pdf", pdfFile.toString())));
        when(ctx.isTranslateToSupported()).thenReturn(true);

        List<Document> documents = ingestionService.ingest(ctx);

        assertNotNull(documents);
        assertFalse(documents.isEmpty());

        verify(vectorStore).accept(anyList());

        verify(translationService, atLeastOnce()).translate(anyString(), eq("en-US"), anyString());
    }

    @Test
    void givenInvalidLanguage_shouldHandleLanguageDetectionFailure() throws Exception {
        when(translationService.detectLanguage(anyString())).thenThrow(new RuntimeException("Error detecting language."));

        PdfKnowledgeBaseOperationContext ctx = mock(PdfKnowledgeBaseOperationContext.class);
        when(ctx.getFileNamesMappings()).thenReturn(HashBiMap.create(Map.of("", pdfFile.toString())));

        List<Document> documents = ingestionService.ingest(ctx);

        assertTrue(documents.isEmpty(), "No documents should be processed if language detection fails.");
        verify(vectorStore).accept(Collections.emptyList());
    }
}
