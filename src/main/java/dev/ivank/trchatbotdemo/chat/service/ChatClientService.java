package dev.ivank.trchatbotdemo.chat.service;

import dev.ivank.trchatbotdemo.chat.dto.ChatFeedback;
import dev.ivank.trchatbotdemo.chat.dto.ConversationDto;
import dev.ivank.trchatbotdemo.report.domain.Report;
import dev.ivank.trchatbotdemo.report.domain.ReportStatus;
import dev.ivank.trchatbotdemo.report.service.ReportEntityService;
import dev.ivank.trchatbotdemo.security.auth.VisitorOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@VisitorOnly
public class ChatClientService {
    private final ConversationDtoMappingService conversationDtoMappingService;
    private final ReportEntityService reportEntityService;

    @Autowired
    public ChatClientService(ConversationDtoMappingService conversationDtoMappingService,
                             ReportEntityService reportEntityService) {
        this.conversationDtoMappingService = conversationDtoMappingService;
        this.reportEntityService = reportEntityService;
    }

    public ConversationDto getOngoingConversation(Report report) {
        return conversationDtoMappingService.toDto(report);
    }

    public ReportStatus changeConversationStatus(Report report, ChatFeedback feedback) {
        if (feedback.isPositive()) {
            report.setReportStatus(ReportStatus.COMPLETED_EXPLICITLY);
        } else if (feedback.isNegative()) {
            report.setReportStatus(ReportStatus.NOT_YET_COMPLETED);
        }
        reportEntityService.save(report);
        return report.getReportStatus();
    }
}
