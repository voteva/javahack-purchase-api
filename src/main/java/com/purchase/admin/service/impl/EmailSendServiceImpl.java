package com.purchase.admin.service.impl;

import com.purchase.admin.config.properties.MailProperties;
import com.purchase.admin.domain.Client;
import com.purchase.admin.domain.PurchaseEmail;
import com.purchase.admin.domain.SearchHistory;
import com.purchase.admin.domain.Supplier;
import com.purchase.admin.repository.EmailRepository;
import com.purchase.admin.service.ClientService;
import com.purchase.admin.service.EmailSendService;
import com.purchase.admin.service.HistoryService;
import com.purchase.admin.service.SuppliersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Slf4j
@Service
public class EmailSendServiceImpl
        implements EmailSendService {
    private static final String CLIENT_ID_PREFIX = "\n\n\n Идентификатор клиента: ";

    private Session session;

    private MailProperties mailProperties;
    private EmailRepository emailRepository;
    private ClientService clientService;
    private SuppliersService suppliersService;
    private HistoryService historyService;

    public EmailSendServiceImpl(@Qualifier("smtpSession") Session session,
                                MailProperties mailProperties,
                                EmailRepository emailRepository,
                                ClientService clientService,
                                SuppliersService suppliersService,
                                HistoryService historyService) {
        this.session = session;
        this.mailProperties = mailProperties;
        this.emailRepository = emailRepository;
        this.clientService = clientService;
        this.suppliersService = suppliersService;
        this.historyService = historyService;
    }

    @Override
    public void sendText(@Nonnull String from, @Nonnull String to, @Nonnull String subject, @Nonnull String body) {
        sendEmail(from, to, subject, body);
    }

    @Override
    public void sendHTML(@Nonnull String from, @Nonnull String to, @Nonnull String subject, @Nonnull String body) {
        sendEmail(from, to, subject, body);
    }

    @Override
    @Transactional
    public void sendEmail(@Nonnull UUID supplierUid, Long clientId, @Nonnull String to) {
        log.info("Send email to {}", to);
        Supplier supplier = suppliersService.getSupplier(supplierUid);

        final SearchHistory history = historyService.getHistoryQueryBySupplierUid(supplierUid);
        String content = history.getMessage();

        Client client = clientService.getClient(clientId);
        content += CLIENT_ID_PREFIX + client.getClientUid();


        sendText(mailProperties.getDefaultReplyTo(),
                to,
                mailProperties.getDefaultSubject(),
                content);

        PurchaseEmail email = PurchaseEmail.builder()
                .client(client)
                .supplierUid(supplier.getSupplierUid())
                .emailFrom(mailProperties.getDefaultReplyTo())
                .emailTo(to)
                .subject(mailProperties.getDefaultSubject())
                .content(content)
                .build();
        emailRepository.save(email);
    }

    private void sendEmail(String from, String to, String subject, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            log.warn("Failed to send email. '{}'", e.getMessage());
        }
    }
}
