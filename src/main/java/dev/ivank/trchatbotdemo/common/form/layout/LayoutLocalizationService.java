package dev.ivank.trchatbotdemo.common.form.layout;

import dev.ivank.trchatbotdemo.common.i18n.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class LayoutLocalizationService extends LocalizationService {
    @Override
    protected String provideBaseBundleBeanName() {
        return localizationProperties.getPath("layout").concat("Bundles");
    }

    @Override
    @Autowired
    public void setMessageSource(@Qualifier("layoutBundles") MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
