package dev.ivank.trchatbotdemo.security.auth.service.form;

import dev.ivank.trchatbotdemo.security.auth.domain.Role;
import dev.ivank.trchatbotdemo.security.auth.service.i18n.HomeLocalizationService;
import dev.ivank.trchatbotdemo.common.form.AbstractBaseFormOptionsService;
import dev.ivank.trchatbotdemo.common.form.BaseFormOptionsDto;
import dev.ivank.trchatbotdemo.common.form.RestForm;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class HomeFormOptionsService<DTO extends BaseFormOptionsDto> extends AbstractBaseFormOptionsService<DTO, RestForm<DTO>> {
    @Autowired
    protected HomeLocalizationService localizationService;

    @Override
    public void prepareFormOptions(RestForm<DTO> form) {
        super.prepareFormOptions(form);
    }

    protected abstract Role getUserRole();
}
