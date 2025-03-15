package dev.ivank.trchatbotdemo.kb.service.form;

import dev.ivank.trchatbotdemo.common.form.AbstractBaseFormOptionsService;
import dev.ivank.trchatbotdemo.common.form.AuthenticatedFormOptionsHelper;
import dev.ivank.trchatbotdemo.common.form.RestForm;
import dev.ivank.trchatbotdemo.security.auth.EmployeeOnly;
import dev.ivank.trchatbotdemo.kb.QaValidationProperties;
import dev.ivank.trchatbotdemo.kb.service.i18n.KnowledgeBaseLocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@EmployeeOnly
public class KnowledgeBaseFormOptionsService extends AbstractBaseFormOptionsService<KnowledgeBaseFormOptionsDto,
        RestForm<KnowledgeBaseFormOptionsDto>> implements AuthenticatedFormOptionsHelper {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileUploadSize;
    private final KnowledgeBaseLocalizationService localizationService;
    private final QaValidationProperties qaValidationProperties;

    @Autowired
    public KnowledgeBaseFormOptionsService(KnowledgeBaseLocalizationService localizationService,
                                           QaValidationProperties qaValidationProperties) {
        this.localizationService = localizationService;
        this.qaValidationProperties = qaValidationProperties;
    }

    @Override
    public void prepareFormOptions(RestForm<KnowledgeBaseFormOptionsDto> form) {
        super.prepareFormOptions(form);
        KnowledgeBaseFormOptionsDto dto = prepareUserData(form);
        dto.setMaxUploadFileSize(maxFileUploadSize);
        dto.setQaValidation(qaValidationProperties);
        dto.getBundle().putAll(localizationService.getMessages("page"));
    }
}
