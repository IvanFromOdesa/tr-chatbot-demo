package dev.ivank.trchatbotdemo.kb.service.qa;

import dev.ivank.trchatbotdemo.common.i18n.Language;
import dev.ivank.trchatbotdemo.kb.ctx.KnowledgeBaseOperationContext;
import dev.ivank.trchatbotdemo.kb.domain.QAEntity;
import dev.ivank.trchatbotdemo.kb.domain.TranslateStatus;
import dev.ivank.trchatbotdemo.kb.dto.QAUpdateDto;
import dev.ivank.trchatbotdemo.kb.service.ContextOperationRecordService;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QAContextOperationRecordService extends ContextOperationRecordService<QAUpdateDto, KnowledgeBaseOperationContext<QAUpdateDto>, QAEntity, QAEntityService> {
    @Override
    protected List<QAEntity> transformToDomain(KnowledgeBaseOperationContext<QAUpdateDto> ctx, List<Document> storedEmbeddings) {
        Map<String, QAEntity> entityLookup = new HashMap<>(storedEmbeddings.size());

        List<QAEntity> entities = storedEmbeddings.stream()
                .map(qa -> {
                    QAEntity entity = new QAEntity();
                    Map<String, Object> metadata = qa.getMetadata();

                    getModify(ctx.getInput(), qa).ifPresent(dto -> entity.setId(dto.getDbId()));

                    entity.setQuestion((String) metadata.get("question"));
                    entity.setAnswer((String) metadata.get("answer"));
                    entity.setLanguage(Language.byAlias((String) metadata.get("language")));
                    entity.setVectorStoreId(getUUID(qa));

                    entity.setTranslateStatus(
                            metadata.containsKey("ogLanguage") ? TranslateStatus.ORIGINAL : TranslateStatus.TRANSLATED
                    );

                    entityLookup.put(qa.getId(), entity);

                    return entity;
                })
                .collect(Collectors.toList());

        setParentQAs(storedEmbeddings, entityLookup);

        return entities;
    }

    private static void setParentQAs(List<Document> storedEmbeddings, Map<String, QAEntity> entityLookup) {
        storedEmbeddings.forEach(qa -> {
            String ogQAId = (String) qa.getMetadata().get("ogQa");
            if (ogQAId != null) {
                QAEntity child = entityLookup.get(qa.getId());
                QAEntity parent = entityLookup.get(ogQAId);
                if (child != null && parent != null) {
                    child.setOriginalQA(parent);
                }
            }
        });
    }


    private static Optional<QAUpdateDto> getModify(List<QAUpdateDto> input, Document qa) {
        return input
                .stream()
                .filter(dto -> {
                    UUID id = dto.getVectorStoreId();
                    return id != null && id.equals(getUUID(qa));
                })
                .findAny();
    }

    private static UUID getUUID(Document qa) {
        return UUID.fromString(qa.getId());
    }
}
