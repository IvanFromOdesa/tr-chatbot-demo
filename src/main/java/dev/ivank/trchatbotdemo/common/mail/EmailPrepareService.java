package dev.ivank.trchatbotdemo.common.mail;

import dev.ivank.trchatbotdemo.common.i18n.LocalizationService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EmailPrepareService {
    public NotificationEmail prepareEmail(String recipient, LocalizationService localizationService, Object... bodyPlaceholders) {
        return prepareEmail(recipient, localizationService, LocaleContextHolder.getLocale(), bodyPlaceholders);
    }

    public NotificationEmail prepareEmail(String recipient, LocalizationService localizationService, Locale locale, Object... bodyPlaceholders) {
        NotificationEmail email = new NotificationEmail();
        email.setRecipient(recipient);
        return fillEmail(localizationService, locale, email, bodyPlaceholders);
    }

    public <EMAIL_CONTEXT extends NotificationEmail> EMAIL_CONTEXT prepareEmail(EMAIL_CONTEXT email, LocalizationService localizationService, Locale locale, Object... bodyPlaceholders) {
        return fillEmail(localizationService, locale, email, bodyPlaceholders);
    }

    private static <EMAIL_CONTEXT extends NotificationEmail> EMAIL_CONTEXT fillEmail(LocalizationService localizationService, Locale locale, EMAIL_CONTEXT email, Object[] bodyPlaceholders) {
        email.setSubject(localizationService.getMessage("mail.subject", locale));
        email.setTitle(localizationService.getMessage("mail.title", locale));
        email.setMessage(localizationService.getMessage("mail.body", locale, bodyPlaceholders));
        return email;
    }
}
