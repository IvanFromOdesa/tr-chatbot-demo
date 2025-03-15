package dev.ivank.trchatbotdemo.report.service;

import dev.ivank.trchatbotdemo.common.AbstractEntityService;
import dev.ivank.trchatbotdemo.report.domain.Report;
import dev.ivank.trchatbotdemo.report.dao.ReportDao;
import dev.ivank.trchatbotdemo.security.auth.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReportEntityService extends AbstractEntityService<Report, Long, ReportDao> {
    @Transactional
    public Optional<Report> getByIdAndUser(Long id, User user) {
       return dao.findByIdAndUser(id, user);
    }
}
