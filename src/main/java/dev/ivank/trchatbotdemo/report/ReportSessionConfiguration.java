package dev.ivank.trchatbotdemo.report;

import dev.ivank.trchatbotdemo.report.service.ReportEntityService;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportSessionConfiguration {
    @Bean
    public ServletListenerRegistrationBean<ReportCompleteSessionListener>
    reportCompleteSessionListenerServletListenerRegistrationBean(ReportEntityService reportEntityService) {

        ServletListenerRegistrationBean<ReportCompleteSessionListener> listenerRegBean =
                new ServletListenerRegistrationBean<>();
        listenerRegBean.setListener(new ReportCompleteSessionListener(reportEntityService));
        return listenerRegBean;
    }
}
