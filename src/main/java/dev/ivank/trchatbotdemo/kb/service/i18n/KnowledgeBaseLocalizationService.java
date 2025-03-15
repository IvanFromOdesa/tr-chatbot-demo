package dev.ivank.trchatbotdemo.kb.service.i18n;

import dev.ivank.trchatbotdemo.common.i18n.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeBaseLocalizationService extends LocalizationService {
    @Override
    protected String provideBaseBundleBeanName() {
        return localizationProperties.getPath("knowledgeBase").concat("Bundles");
    }

    @Override
    @Autowired
    public void setMessageSource(@Qualifier("knowledgeBaseBundles") MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
