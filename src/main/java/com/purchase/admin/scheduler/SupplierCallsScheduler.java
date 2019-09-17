package com.purchase.admin.scheduler;

import com.purchase.admin.config.properties.TopicsProperties;
import com.purchase.admin.redis.domain.SupplierCallTask;
import com.purchase.admin.redis.service.MessageSubscriberService;
import com.purchase.admin.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierCallsScheduler {

    private final CallService callService;
    private final TopicsProperties topicsProperties;
    private final MessageSubscriberService messageSubscriberService;

    @Scheduled(fixedRate = 10000)
    public void scheduleCalls() {
        final SupplierCallTask task = messageSubscriberService.getOne(
                topicsProperties.getSupplierCallTopic(),
                SupplierCallTask.class);

        if (task != null) {
            callService.call(task.getSupplierUid(), task.getPhone());
        }
    }
}
