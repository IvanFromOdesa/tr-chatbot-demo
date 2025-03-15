package dev.ivank.trchatbotdemo.kb;

import dev.ivank.trchatbotdemo.common.io.PdfProcessingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PdfProcessingServiceTests {
    private Path tempDir;
    private Path pdfFile;
    @InjectMocks
    private PdfProcessingService processingService;

    @BeforeEach
    void setup() throws IOException {
        tempDir = KbTestUtils.getTempDirectory();
        pdfFile = KbTestUtils.copyTestPdfToTempDir(tempDir.resolve("test.pdf"));
        ReflectionTestUtils.setField(
                processingService,
                "uploadDir",
                tempDir.toString()
        );
    }

    @AfterEach
    void tearDown() {
        KbTestUtils.clearTempDir(tempDir);
    }

    @Test
    void givenValidPdfFilePath_shouldGenerateThumbnail() {
        String pdfPath = savePdf();
        assertTrue(Files.exists(Paths.get(pdfPath.replace(".pdf", "_thumbnail.png"))));
    }

    @Test
    void givenNonPdfPath_shouldThrowException() throws IOException {
        Path dummyTxt = KbTestUtils.copyTestPdfToTempDir(tempDir.resolve("dummy.txt"));
        RuntimeException e = assertThrows(
                RuntimeException.class,
                () -> processingService.generateThumbnail(dummyTxt.toString())
        );

        assertTrue(e.getMessage().contains("error.fileNotPdf"));
    }

    @Test
    void givenNonExistingPath_shouldThrowException() {
        RuntimeException e = assertThrows(
                RuntimeException.class,
                () -> processingService.generateThumbnail("nonexitstingfile.pdf")
        );

        assertTrue(e.getMessage().contains("error.pdfThumbnailFail"));
    }

    @Test
    void givenValidPdfFileName_shouldGetThumbnailPath() {
        String pdfPath = savePdf();
        Path thumbnailPath = processingService.getThumbnailPath(pdfPath);
        assertTrue(Files.exists(thumbnailPath));
    }

    private String savePdf() {
        String pdfPath = pdfFile.toString();
        processingService.generateThumbnail(pdfPath);
        return pdfPath;
    }
}
