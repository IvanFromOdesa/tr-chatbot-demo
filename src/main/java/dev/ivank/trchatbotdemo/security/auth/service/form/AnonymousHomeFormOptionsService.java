package dev.ivank.trchatbotdemo.security.auth.service.form;

import dev.ivank.trchatbotdemo.security.auth.domain.Role;
import dev.ivank.trchatbotdemo.common.form.BaseFormOptionsDto;
import dev.ivank.trchatbotdemo.common.form.RestForm;
import org.springframework.stereotype.Service;

@Service
public class AnonymousHomeFormOptionsService extends HomeFormOptionsService<BaseFormOptionsDto> {
    @Override
    public void prepareFormOptions(RestForm<BaseFormOptionsDto> form) {
        super.prepareFormOptions(form);
        form.getDto().getBundle().putAll(localizationService.getMessages("anonymous"));
    }

    @Override
    protected Role getUserRole() {
        return Role.ANONYMOUS;
    }
}
