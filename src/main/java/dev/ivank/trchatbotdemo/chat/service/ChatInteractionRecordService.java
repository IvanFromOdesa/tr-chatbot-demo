package dev.ivank.trchatbotdemo.chat.service;

import dev.ivank.trchatbotdemo.chat.domain.ChatInteraction;
import dev.ivank.trchatbotdemo.chat.dto.ChatInteractionRequest;
import dev.ivank.trchatbotdemo.report.domain.Report;
import dev.ivank.trchatbotdemo.report.domain.ReportStatus;
import dev.ivank.trchatbotdemo.report.service.ReportEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatInteractionRecordService {
    private final ChatInteractionEntityService chatInteractionEntityService;
    private final ReportEntityService reportEntityService;

    @Autowired
    public ChatInteractionRecordService(ChatInteractionEntityService chatInteractionEntityService,
                                        ReportEntityService reportEntityService) {
        this.chatInteractionEntityService = chatInteractionEntityService;
        this.reportEntityService = reportEntityService;
    }

    @Transactional
    public Report recordChatInteraction(ChatInteractionRequest request) {
        ChatInteraction chatInteraction = new ChatInteraction();
        Report currentReport = request.getCurrentReport();

        if (currentReport.getReportStatus().isInitialized()) {
            currentReport.setReportStatus(ReportStatus.NOT_YET_COMPLETED);
        }

        chatInteraction.setAskedAt(request.getAskedAt());
        chatInteraction.setReport(reportEntityService.save(currentReport));
        chatInteraction.setQuestion(request.getUserPrompt());
        chatInteraction.setQuestionLanguage(request.getUserLanguage());
        chatInteraction.setAnswer(request.getResponse());
        chatInteraction.setTimeToProcess(request.getTimeToProcess());
        chatInteraction.setNoContext(request.isNoContext());

        chatInteractionEntityService.save(chatInteraction);

        return currentReport;
    }
}

