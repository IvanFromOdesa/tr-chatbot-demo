package dev.ivank.trchatbotdemo.report.dao;

import dev.ivank.trchatbotdemo.report.domain.Report;
import dev.ivank.trchatbotdemo.security.auth.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportDao extends JpaRepository<Report, Long> {
    @EntityGraph(Report.CHAT_INTERACTIONS_EAGER)
    Optional<Report> findByIdAndUser(Long id, User user);
}
