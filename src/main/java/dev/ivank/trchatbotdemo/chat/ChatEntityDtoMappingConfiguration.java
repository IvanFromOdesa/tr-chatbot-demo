package dev.ivank.trchatbotdemo.chat;

import dev.ivank.trchatbotdemo.chat.dto.ChatInteractionDto;
import dev.ivank.trchatbotdemo.chat.dto.ConversationDto;
import dev.ivank.trchatbotdemo.common.mapper.MappingContext;
import dev.ivank.trchatbotdemo.report.domain.Report;
import dev.ivank.trchatbotdemo.report.service.ReportEntityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ChatEntityDtoMappingConfiguration {
    @Bean
    public MappingContext<Report, ConversationDto> reportConversationDtoMappingContext(ReportEntityService reportEntityService) {
        MappingContext<Report, ConversationDto> ctx = new MappingContext<>(Report.class, ConversationDto.class);
        return ctx
                .newBuilder()
                .useNonNull()
                .useStrict()
                .addDtoMapping(Report::getReportStatus, ConversationDto::setStatus)
                .addDtoFieldProvider(
                        ConversationDtoConvertHelper.getChatInteractionSetProvider(reportEntityService),
                        report -> report,
                        ConversationDto::setChatInteractions
                )
                .build();
    }

    private static class ConversationDtoConvertHelper {
        private static Function<Report, Set<ChatInteractionDto>> getChatInteractionSetProvider(ReportEntityService reportEntityService) {
            return report -> {
                // Id is null, report is not persisted by Hibernate yet.
                // Meaning that its report status is set to INITIALIZED
                // and no chat interactions were recorded yet.
                if (report.getReportStatus().isInitialized()) {
                    return new LinkedHashSet<>();
                } else {
                    Optional<Report> byId = reportEntityService.getByIdAndUser(report.getId(), report.getUser());
                    return byId.map(value -> value
                            .getChatInteractions()
                            .stream()
                            .map(c -> {
                                ChatInteractionDto dto = new ChatInteractionDto();
                                dto.setId("chat-interaction-%s".formatted(UUID.randomUUID()));
                                dto.setQuestion(c.getQuestion());
                                dto.setAnswer(c.getAnswer());
                                dto.setAskedAt(c.getAskedAt());
                                dto.setQuestionLanguage(c.getQuestionLanguage());
                                dto.setTimeToProcess(c.getTimeToProcess());
                                return dto;
                            })
                            .collect(Collectors.toCollection(LinkedHashSet::new))).orElseGet(LinkedHashSet::new);
                }
            };
        }
    }
}
