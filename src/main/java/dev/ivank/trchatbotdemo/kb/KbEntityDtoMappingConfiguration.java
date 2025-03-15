package dev.ivank.trchatbotdemo.kb;

import dev.ivank.trchatbotdemo.common.form.UserDataDto;
import dev.ivank.trchatbotdemo.common.form.UserDataDtoEntityHelper;
import dev.ivank.trchatbotdemo.common.mapper.MappingContext;
import dev.ivank.trchatbotdemo.kb.domain.PdfEntity;
import dev.ivank.trchatbotdemo.kb.domain.QAEntity;
import dev.ivank.trchatbotdemo.kb.domain.TranslateStatus;
import dev.ivank.trchatbotdemo.kb.dto.PdfResponseDto;
import dev.ivank.trchatbotdemo.kb.dto.QAResponseDto;
import dev.ivank.trchatbotdemo.security.auth.domain.User;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KbEntityDtoMappingConfiguration {
    @Bean
    public MappingContext<QAEntity, QAResponseDto> entityQAResponseDtoMappingContext() {
        MappingContext<QAEntity, QAResponseDto> ctx = new MappingContext<>(QAEntity.class, QAResponseDto.class);
        return ctx
                .newBuilder()
                .useStrict()
                .useNonNull()
                .addDtoMapping(QAEntity::getQuestion, QAResponseDto::setQ)
                .addDtoMapping(QAEntity::getAnswer, QAResponseDto::setA)
                .addDtoMapping(QAEntity::getId, QAResponseDto::setDbId)
                .addFieldConverterOnDto(
                        QAResponseDtoConvertHelper.getTranslateStatusConverter(),
                        QAEntity::getTranslateStatus,
                        QAResponseDto::setTranslateStatus
                )
                .addFieldConverterOnDto(
                        getUserConverter(),
                        QAEntity::getUploadedBy,
                        QAResponseDto::setUploadedBy
                )
                .addFieldConverterOnDto(
                        QAResponseDtoConvertHelper.getOriginalQAConverter(),
                        QAEntity::getOriginalQA,
                        QAResponseDto::setOriginalQdbId
                )
                .build();
    }

    @Bean
    public MappingContext<PdfEntity, PdfResponseDto> entityPdfResponseDtoMappingContext() {
        MappingContext<PdfEntity, PdfResponseDto> ctx = new MappingContext<>(PdfEntity.class, PdfResponseDto.class);
        return ctx
                .newBuilder()
                .useStrict()
                .useNonNull()
                .addFieldConverterOnDto(
                        getUserConverter(),
                        PdfEntity::getUploadedBy,
                        PdfResponseDto::setUploadedBy
                )
                .build();
    }

    private static class QAResponseDtoConvertHelper {
        private static Converter<TranslateStatus, Integer> getTranslateStatusConverter() {
            return ctx -> ctx.getSource().getKey();
        }

        private static Converter<QAEntity, Long> getOriginalQAConverter() {
            return ctx -> {
                QAEntity source = ctx.getSource();
                return source != null ? source.getId() : null;
            };
        }
    }

    private static Converter<User, UserDataDto> getUserConverter() {
        return ctx -> UserDataDtoEntityHelper.getUserData(ctx.getSource());
    }
}
