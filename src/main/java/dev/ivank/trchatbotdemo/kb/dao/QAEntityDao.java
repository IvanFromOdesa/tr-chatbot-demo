package dev.ivank.trchatbotdemo.kb.dao;

import dev.ivank.trchatbotdemo.kb.domain.QAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QAEntityDao extends JpaRepository<QAEntity, Long> {
}
