package dev.ivank.trchatbotdemo.common.form;

import org.springframework.security.core.Authentication;

public class RestForm<DTO extends GenericFormOptionsDto> {
    protected String bundleName;
    protected Authentication authentication;
    private DTO dto;

    public RestForm(DTO dto) {
        this.dto = dto;
    }

    public RestForm(DTO dto, String bundleName) {
        this.dto = dto;
        this.bundleName = bundleName;
    }

    public RestForm(DTO dto, Authentication authentication) {
        this.dto = dto;
        this.authentication = authentication;
    }

    public RestForm(DTO dto, String bundleName, Authentication authentication) {
        this.dto = dto;
        this.bundleName = bundleName;
        this.authentication = authentication;
    }

    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public DTO getDto() {
        return dto;
    }

    public void setDto(DTO dto) {
        this.dto = dto;
    }
}
