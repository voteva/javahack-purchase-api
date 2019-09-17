package com.purchase.admin.service.impl;

import com.purchase.admin.config.properties.MailProperties;
import com.purchase.admin.service.EmailSendService;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

import java.util.UUID;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@Slf4j
@RequiredArgsConstructor
public class SendGridEmailSendServiceImpl
        implements EmailSendService {

    private final SendGrid sendGridClient;
    private final MailProperties mailProperties;

    @Override
    public void sendText(@Nonnull String from, @Nonnull String to, @Nonnull String subject, @Nonnull String body) {
        sendEmail(from, to, subject, new Content(TEXT_PLAIN_VALUE, body));
    }

    @Override
    public void sendHTML(@Nonnull String from, @Nonnull String to, @Nonnull String subject, @Nonnull String body) {
        sendEmail(from, to, subject, new Content(TEXT_HTML_VALUE, body));
    }

    @Override
    public void sendEmail(@Nonnull UUID supplierUid, Long clientId, @Nonnull String to) {
        return;
    }

    private void sendEmail(@Nonnull String from, @Nonnull String to, @Nonnull String subject, @Nonnull Content content) {
        final Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        mail.setReplyTo(new Email(mailProperties.getDefaultReplyTo()));

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGridClient.api(request);

            if (log.isDebugEnabled()) {
                log.debug("Success to send email, statusCode '{}', body '{}', headers '{}'",
                        response.getStatusCode(), response.getBody(), response.getHeaders());
            }
        } catch (Exception e) {
            log.warn("Failed to send email. '{}'", e.getMessage());
        }
    }
}
