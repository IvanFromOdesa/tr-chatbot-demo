package dev.ivank.trchatbotdemo.kb.service.form;

import dev.ivank.trchatbotdemo.common.form.AuthenticatedFormOptionsDto;
import dev.ivank.trchatbotdemo.kb.QaValidationProperties;

public class KnowledgeBaseFormOptionsDto extends AuthenticatedFormOptionsDto {
    private String maxUploadFileSize;
    private QaValidationProperties qaValidation;

    public String getMaxUploadFileSize() {
        return maxUploadFileSize;
    }

    public void setMaxUploadFileSize(String maxUploadFileSize) {
        this.maxUploadFileSize = maxUploadFileSize;
    }

    public QaValidationProperties getQaValidation() {
        return qaValidation;
    }

    public void setQaValidation(QaValidationProperties qaValidation) {
        this.qaValidation = qaValidation;
    }
}
