package dev.ivank.trchatbotdemo.kb.service.qa;

import dev.ivank.trchatbotdemo.common.AbstractEntityService;
import dev.ivank.trchatbotdemo.kb.dao.QAEntityDao;
import dev.ivank.trchatbotdemo.kb.domain.QAEntity;
import org.springframework.stereotype.Service;

@Service
public class QAEntityService extends AbstractEntityService<QAEntity, Long, QAEntityDao> {
}
