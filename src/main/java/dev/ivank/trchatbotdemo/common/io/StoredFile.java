package dev.ivank.trchatbotdemo.common.io;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public record StoredFile(String originalFilename, byte[] content) {
    public static StoredFile fromMultipartFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            if (inputStream.available() == 0) {
                throw new RuntimeException("error.emptyFile");
            }
            return new StoredFile(file.getOriginalFilename(), inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("error.io", e);
        }
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(content);
    }
}

