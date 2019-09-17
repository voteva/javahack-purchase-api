package com.purchase.admin.scheduler;

import com.purchase.admin.config.properties.TopicsProperties;
import com.purchase.admin.redis.domain.SupplierEmailTask;
import com.purchase.admin.redis.service.MessageSubscriberService;
import com.purchase.admin.service.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierEmailsScheduler {

    private final TopicsProperties topicsProperties;
    private final EmailSendService emailSendService;
    private final MessageSubscriberService messageSubscriberService;

    @Scheduled(fixedRate = 3000)
    public void scheduleEmails() {
        final SupplierEmailTask task = messageSubscriberService.getOne(
                topicsProperties.getSupplierEmailTopic(),
                SupplierEmailTask.class);

        if (task != null) {
            emailSendService.sendEmail(task.getSupplierUid(), task.getClientId(), task.getToAddress());
        }
    }
}
