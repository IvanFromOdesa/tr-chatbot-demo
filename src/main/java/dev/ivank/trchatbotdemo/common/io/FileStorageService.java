package dev.ivank.trchatbotdemo.common.io;

import dev.ivank.trchatbotdemo.common.EntityNotFoundException;
import dev.ivank.trchatbotdemo.common.MessageLogger;
import dev.ivank.trchatbotdemo.common.util.CommonUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class FileStorageService {
    @Value("${file.upload.directory}")
    private String path;
    private Path UPLOAD_DIR;

    @PostConstruct
    public void init() throws IOException {
        UPLOAD_DIR = Paths.get(path);
        if (!Files.exists(UPLOAD_DIR)) {
            Files.createDirectories(UPLOAD_DIR);
        }
    }

    public Map<String, String> saveFiles(List<StoredFile> files) {
        Map<String, String> savedFilePaths = new HashMap<>();

        for (StoredFile file : files) {
            String originalFilename = file.originalFilename();
            String fileExtension = com.google.common.io.Files
                    .getFileExtension(Objects.requireNonNull(originalFilename));

            String uniqueFileName = generateFileName().concat(".").concat(fileExtension);
            Path filePath = UPLOAD_DIR.resolve(uniqueFileName);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                MessageLogger.error(e.getLocalizedMessage());
                throw new RuntimeException("error.io", e);
            }

            savedFilePaths.put(originalFilename, filePath.toString());
        }
        return savedFilePaths;
    }

    public Resource loadFile(String fileName) {
        Path filePath = UPLOAD_DIR.resolve(fileName).normalize();

        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            MessageLogger.error(e.getLocalizedMessage());
            throw new RuntimeException("error.malformedUrl", e);
        }

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new EntityNotFoundException("error.notFound", fileName);
        }
    }

    private static String generateFileName() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH mm ss_z");
        return CommonUtils
                .SRAlphanumeric(5, 10)
                .concat("_")
                .concat(formatter.format(
                        Instant.now().atZone(ZoneId.systemDefault())
                ));
    }
}
