package dev.ivank.trchatbotdemo.kb.service.pdf;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dev.ivank.trchatbotdemo.common.io.StoredFile;
import dev.ivank.trchatbotdemo.kb.ctx.KnowledgeBaseOperationContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PdfKnowledgeBaseOperationContext extends KnowledgeBaseOperationContext<StoredFile> {
    private final BiMap<String, String> fileNamesMappings;

    public PdfKnowledgeBaseOperationContext(List<MultipartFile> input) {
        super(toStoredFiles(input));
        this.fileNamesMappings = HashBiMap.create();
    }

    public PdfKnowledgeBaseOperationContext(List<MultipartFile> input, boolean translateToSupported) {
        super(toStoredFiles(input), translateToSupported);
        this.fileNamesMappings = HashBiMap.create();
    }

    private static List<StoredFile> toStoredFiles(List<MultipartFile> input) {
        return input.stream().map(StoredFile::fromMultipartFile).toList();
    }

    public BiMap<String, String> getFileNamesMappings() {
        return fileNamesMappings;
    }
}
