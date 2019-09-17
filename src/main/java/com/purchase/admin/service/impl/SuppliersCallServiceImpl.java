package com.purchase.admin.service.impl;

import com.purchase.admin.config.properties.MailProperties;
import com.purchase.admin.domain.ApplicationSettings;
import com.purchase.admin.domain.SearchHistory;
import com.purchase.admin.domain.Supplier;
import com.purchase.admin.enumeration.ContactType;
import com.purchase.admin.model.in.CallSupplierRequest;
import com.purchase.admin.redis.domain.SupplierCallTask;
import com.purchase.admin.redis.domain.SupplierEmailTask;
import com.purchase.admin.redis.service.MessagePublisherService;
import com.purchase.admin.service.ApplicationSettingsService;
import com.purchase.admin.service.HistoryService;
import com.purchase.admin.service.SuppliersCallService;
import com.purchase.admin.service.SuppliersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class SuppliersCallServiceImpl
        implements SuppliersCallService {

    private final MessagePublisherService messagePublisherService;
    private final HistoryService historyService;
    private final SuppliersService suppliersService;
    private final ApplicationSettingsService settingsService;
    private final MailProperties mailProperties;

    @Override
    public void call(@Nonnull List<CallSupplierRequest> requests, @Nonnull Long clientId) {
        final ApplicationSettings settings = settingsService.loadSettings();

        requests.forEach(request -> {
            final Supplier supplier = suppliersService.getSupplier(request.getSupplierUid());

            if (request.getContactTypes().contains(ContactType.CALL) && isNotEmpty(supplier.getPhone())) {
                publishCallTask(supplier, settings);
            }
            if (request.getContactTypes().contains(ContactType.EMAIL) && isNotEmpty(supplier.getEmail())) {
                publishEmailTask(supplier, settings);
            }
        });
    }

    private void publishCallTask(@Nonnull Supplier supplier,
                                 @Nonnull ApplicationSettings settings) {
        final String supplierPhone = settings.getTestMode()
                ? settings.getPhone()
                : supplier.getPhone();

        final SupplierCallTask callTask = new SupplierCallTask()
                .setTaskUid(UUID.randomUUID())
                .setPhone(supplierPhone)
                .setSupplierUid(supplier.getSupplierUid());

        messagePublisherService.publish(callTask);
    }

    private void publishEmailTask(@Nonnull Supplier supplier,
                                  @Nonnull ApplicationSettings settings) {
        final String supplierEmail = settings.getTestMode()
                ? settings.getEmail()
                : supplier.getEmail();

        final SearchHistory history = historyService.getHistoryQueryBySupplierUid(supplier.getSupplierUid());

        final SupplierEmailTask emailTask = new SupplierEmailTask()
                .setTaskUid(UUID.randomUUID())
                .setFromAddress(mailProperties.getDefaultReplyTo())
                .setToAddress(supplierEmail)
                .setSubject(mailProperties.getDefaultSubject())
                .setClientId(history.getClientId())
                .setSupplierUid(supplier.getSupplierUid())
                .setMessage(history.getMessage());

        messagePublisherService.publish(emailTask);
    }
}
