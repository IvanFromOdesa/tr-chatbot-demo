package dev.ivank.trchatbotdemo.chat.service;

import dev.ivank.trchatbotdemo.security.auth.VisitorOnly;
import dev.ivank.trchatbotdemo.common.i18n.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@VisitorOnly
public class ChatLocalizationService extends LocalizationService {
    @Override
    protected String provideBaseBundleBeanName() {
        return localizationProperties.getPath("chat").concat("Bundles");
    }

    @Override
    @Autowired
    public void setMessageSource(@Qualifier("chatBundles") MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
