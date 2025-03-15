package dev.ivank.trchatbotdemo.common.valid;

public class FormValidationContext<DTO> extends CommonValidationContext {
    protected final DTO dto;

    public FormValidationContext(DTO dto) {
        super();
        this.dto = dto;
    }

    public DTO getDto() {
        return dto;
    }
}
