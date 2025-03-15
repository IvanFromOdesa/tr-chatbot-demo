package dev.ivank.trchatbotdemo.kb.service.pdf;

import dev.ivank.trchatbotdemo.common.io.StoredFile;
import dev.ivank.trchatbotdemo.kb.domain.PdfEntity;
import dev.ivank.trchatbotdemo.kb.domain.PdfTextChunkEntity;
import dev.ivank.trchatbotdemo.kb.service.ContextOperationRecordService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PdfContextOperationRecordService extends ContextOperationRecordService<StoredFile,
        PdfKnowledgeBaseOperationContext, PdfEntity, PdfEntityService> {
    @Value("${file.upload.directory}")
    private String uploadDirPath;

    @Override
    protected List<PdfEntity> transformToDomain(PdfKnowledgeBaseOperationContext ctx,
                                                List<Document> storedEmbeddings) {
        Map<String, List<Document>> groupedByFileName = storedEmbeddings.stream()
                .collect(Collectors.groupingBy(e -> (String) e.getMetadata().get("file_name")));

        return groupedByFileName.entrySet().stream()
                .map(entry -> {
                    String fileName = entry.getKey();
                    List<Document> chunks = entry.getValue();

                    String filePath = Paths.get(uploadDirPath).resolve(fileName).toString();

                    String originalFileName = ctx.getFileNamesMappings().inverse().get(filePath);

                    PdfEntity pdfEntity = new PdfEntity();
                    pdfEntity.setOriginalFileName(originalFileName);
                    pdfEntity.setFileName(fileName);
                    pdfEntity.setFilePath(filePath);
                    pdfEntity.setSize((long) chunks.get(0).getMetadata().get("file_size"));
                    pdfEntity.setTranslatedToSupported(
                            chunks.stream().anyMatch(d -> d.getMetadata().containsKey("translated"))
                    );

                    final PdfEntity persisted = entityService.save(pdfEntity);

                    Set<PdfTextChunkEntity> textChunks = chunks.stream()
                            .map(chunk -> {
                                PdfTextChunkEntity.PdfTextChunkId chunkId = new PdfTextChunkEntity.PdfTextChunkId();
                                chunkId.setVectorStoreId(
                                        UUID.fromString(chunk.getId())
                                );

                                PdfTextChunkEntity textChunk = new PdfTextChunkEntity();
                                textChunk.setId(chunkId);
                                textChunk.setPdfEntity(persisted);
                                return textChunk;
                            })
                            .collect(Collectors.toSet());

                    persisted.getTextChunks().addAll(textChunks);
                    return persisted;
                })
                .toList();
    }

}
