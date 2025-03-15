package dev.ivank.trchatbotdemo.kb.dao;

import dev.ivank.trchatbotdemo.kb.domain.PdfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfEntityDao extends JpaRepository<PdfEntity, Long> {
}
