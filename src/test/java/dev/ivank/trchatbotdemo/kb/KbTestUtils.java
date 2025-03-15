package dev.ivank.trchatbotdemo.kb;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.stream.Stream;

class KbTestUtils {
    static Path getTempDirectory() throws IOException {
        return Files.createTempDirectory("test-uploads");
    }

    static void clearTempDir(Path tempDir) {
        try (Stream<Path> walk = Files.walk(tempDir)) {
            walk
                    .sorted(Comparator.reverseOrder())
                    .forEach(p -> p.toFile().delete());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Path copyTestPdfToTempDir(Path destinationPath) throws IOException {
        try (InputStream inputStream = KbTestUtils.class.getClassLoader().getResourceAsStream("kb/test.pdf")) {
            if (inputStream == null) {
                throw new IOException("Test PDF file not found: %s".formatted("kb/test.pdf"));
            }
            Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        }
        return destinationPath;
    }
}
