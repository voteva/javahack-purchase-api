package com.purchase.admin.service.impl;

import com.purchase.admin.config.properties.MailProperties;
import com.purchase.admin.domain.Client;
import com.purchase.admin.domain.PurchaseEmail;
import com.purchase.admin.domain.Supplier;
import com.purchase.admin.enumeration.ContactType;
import com.purchase.admin.repository.EmailRepository;
import com.purchase.admin.service.ClientService;
import com.purchase.admin.service.EmailReceiveService;
import com.purchase.admin.service.EmailSendService;
import com.purchase.admin.service.SuppliersService;
import com.purchase.admin.utils.EmailProcessing;
import com.purchase.admin.utils.UUIDParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailReceiveServiceImpl
        implements EmailReceiveService {

    private static final String FWD = "Fwd: ";
    private static final String EMAIL_CONTENT_PREFIX = "Получено сообщение от ";
    private static final String DEFAUL_EMAIL_FOLDER = "inbox";

    private final MailProperties mailProperties;
    private final ClientService clientService;
    private final SuppliersService suppliersService;

    @Qualifier("emailSendServiceImpl")
    private final EmailSendService emailSendService;
    private final EmailRepository emailRepository;

    private void processMessages(Message[] messages) throws MessagingException, IOException {
        for (Message message : messages) {
            String receivedFrom = InternetAddress.toString(message.getFrom());
            String receivedTo = InternetAddress.toString(message
                    .getRecipients(Message.RecipientType.TO));

            String subject = FWD + message.getSubject();
            String content = EmailProcessing.parseContent(message);

            Client client = clientService.findClientByUid(UUIDParser.parse(content));
            PurchaseEmail prevEmail = null;
            List<PurchaseEmail> prevEmailList = emailRepository.findByClientIdAndEmailToContains(client.getId(), receivedFrom);
            if (!prevEmailList.isEmpty()) {
                prevEmail = prevEmailList.get(0);
            }

            if (isCorrectToProcessMessage(receivedFrom, receivedTo, subject, content)) {
                continue;
            }

            PurchaseEmail email = PurchaseEmail.builder()
                    .emailTo(receivedTo)
                    .emailFrom(receivedFrom)
                    .content(content)
                    .subject(subject)
                    .build();

            content = EMAIL_CONTENT_PREFIX + receivedFrom + ": \n\n" + content;

            emailSendService.sendText(mailProperties.getDefaultReplyTo(), client.getEmail(), subject, content);
            emailRepository.save(email);
            updateSupplier(prevEmail, content);
        }
    }

    private void updateSupplier(PurchaseEmail prevEmail, String content) {
        suppliersService.saveSupplierAnswer(prevEmail.getSupplierUid(), content, ContactType.EMAIL);
    }

    private boolean isCorrectToProcessMessage(String receivedFrom, String receivedTo, String subject, String content) {
        return content == null ||
                emailRepository.findByEmailFromAndEmailToAndContentAndSubject(
                        receivedFrom, receivedTo, content, subject).isPresent();
    }

    public void fetch() {
        Session session = Session.getDefaultInstance(mailProperties.getPopProperties());
        try {
            Store store = session.getStore("pop3s");
            store.connect(mailProperties.getPop3sHost(),
                    mailProperties.getUsername(),
                    mailProperties.getPassword());

            Folder folder = store.getFolder(DEFAUL_EMAIL_FOLDER);
            folder.open(Folder.READ_ONLY);

            processMessages(folder.getMessages());

            folder.close(false);
            store.close();
        } catch (Exception e) {
            log.warn("Error during email fetch. {}", e.getMessage());
        }
    }
}