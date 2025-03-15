package dev.ivank.trchatbotdemo.security.auth.service.form;

import dev.ivank.trchatbotdemo.common.form.AuthenticatedFormOptionsDto;
import dev.ivank.trchatbotdemo.common.form.AuthenticatedFormOptionsHelper;
import dev.ivank.trchatbotdemo.common.form.RestForm;

public abstract class AuthenticatedHomeFormOptionsService extends HomeFormOptionsService<AuthenticatedFormOptionsDto> implements AuthenticatedFormOptionsHelper {
    @Override
    public void prepareFormOptions(RestForm<AuthenticatedFormOptionsDto> form) {
        super.prepareFormOptions(form);
        AuthenticatedFormOptionsDto dto = prepareUserData(form);
        dto.getBundle().putAll(localizationService.getMessages(getUserRole().getAlias()));
    }
}
