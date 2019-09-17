package com.purchase.admin.scheduler;

import com.purchase.admin.service.EmailReceiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchEmailScheduler {

    private final EmailReceiveService emailReceiveService;

    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    public void scheduleFixedRateWithInitialDelayTask() {
        emailReceiveService.fetch();
    }
}
