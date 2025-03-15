package dev.ivank.trchatbotdemo.security.auth.service.form;

import dev.ivank.trchatbotdemo.security.auth.service.i18n.LoginLocalizationService;
import dev.ivank.trchatbotdemo.common.form.AbstractBaseFormOptionsService;
import dev.ivank.trchatbotdemo.common.form.BaseFormOptionsDto;
import dev.ivank.trchatbotdemo.common.form.RestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginFormOptionsService extends AbstractBaseFormOptionsService<BaseFormOptionsDto, RestForm<BaseFormOptionsDto>> {
    private final LoginLocalizationService localizationService;

    @Autowired
    public LoginFormOptionsService(LoginLocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @Override
    public void prepareFormOptions(RestForm<BaseFormOptionsDto> form) {
        super.prepareFormOptions(form);
        form.getDto().getBundle().putAll(localizationService.getMessages());
    }
}
