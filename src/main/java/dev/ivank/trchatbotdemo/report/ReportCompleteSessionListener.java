package dev.ivank.trchatbotdemo.report;

import dev.ivank.trchatbotdemo.report.domain.Report;
import dev.ivank.trchatbotdemo.report.domain.ReportStatus;
import dev.ivank.trchatbotdemo.report.service.ReportEntityService;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.time.Instant;
import java.util.Optional;

public class ReportCompleteSessionListener implements HttpSessionListener {
    private final ReportEntityService reportEntityService;

    public ReportCompleteSessionListener(ReportEntityService reportEntityService) {
        this.reportEntityService = reportEntityService;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Report report = (Report) se.getSession().getAttribute("userReport");
        if (report != null) {
            Long id = report.getId();
            if (id != null) {
                Optional<Report> byId = reportEntityService.getById(id);
                if (byId.isPresent()) {
                    if (!byId.get().getReportStatus().isCompleted()) {
                        report.setReportStatus(ReportStatus.COMPLETED_IMPLICITLY);
                    }
                    report.setFinishedAt(Instant.now());
                    reportEntityService.save(report);
                }
            }
        }
    }
}
