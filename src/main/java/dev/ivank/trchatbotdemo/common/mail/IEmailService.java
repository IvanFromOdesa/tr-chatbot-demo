package dev.ivank.trchatbotdemo.common.mail;

public interface IEmailService<EMAIL_CONTEXT extends NotificationEmail> {
    void sendEmail(EMAIL_CONTEXT emailContext);
}
