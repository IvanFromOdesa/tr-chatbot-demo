package dev.ivank.trchatbotdemo.kb;

import dev.ivank.trchatbotdemo.common.EntityNotFoundException;
import dev.ivank.trchatbotdemo.common.io.FileStorageService;
import dev.ivank.trchatbotdemo.common.io.StoredFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class FileStorageServiceTests {
    private Path tempDir;
    @InjectMocks
    private FileStorageService storageService;
    private String originalFilename;
    private StoredFile storedFile;

    @BeforeEach
    void setup() throws IOException {
        tempDir = KbTestUtils.getTempDirectory();
        ReflectionTestUtils.setField(
                storageService,
                "path",
                tempDir.toString()
        );
        storageService.init();

        originalFilename = "testFile.txt";
        byte[] content = "Hello, World!".getBytes();
        storedFile = new StoredFile(originalFilename, content);
    }

    @AfterEach
    void tearDown() {
        KbTestUtils.clearTempDir(tempDir);
    }

    @Test
    void givenFiles_shouldSaveSuccessfully() {
        List<StoredFile> files = List.of(storedFile);

        Map<String, String> result = storageService.saveFiles(files);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(originalFilename));
        assertTrue(Files.exists(Paths.get(result.get(originalFilename))));
    }

    @Test
    void givenExistingFile_shouldLoadSuccessfully() {
        List<StoredFile> files = List.of(storedFile);
        Map<String, String> savedPaths = storageService.saveFiles(files);

        String savedPath = savedPaths.get(originalFilename);
        Resource resource = storageService.loadFile(savedPath);

        assertNotNull(resource);
        assertTrue(resource.exists());
        assertTrue(resource.isReadable());
    }

    @Test
    void givenNonExistingFile_shouldThrowException() {
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> storageService.loadFile("nonexistentfile.txt")
        );

        assertTrue(exception.getMessage().contains("error.notFound"));
    }
}
