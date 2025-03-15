package dev.ivank.trchatbotdemo.security.auth.service.i18n;

import dev.ivank.trchatbotdemo.common.i18n.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class LoginLocalizationService extends LocalizationService {
    @Override
    protected String provideBaseBundleBeanName() {
        return localizationProperties.getPath("login").concat("Bundles");
    }

    @Override
    @Autowired
    public void setMessageSource(@Qualifier("loginBundles") MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
