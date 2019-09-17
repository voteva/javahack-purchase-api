package com.purchase.admin.service;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface EmailSendService {

    void sendText(@Nonnull String from, @Nonnull String to, @Nonnull String subject, @Nonnull String body);

    void sendHTML(@Nonnull String from, @Nonnull String to, @Nonnull String subject, @Nonnull String body);

    void sendEmail(@Nonnull UUID supplierUid, Long clientId, @Nonnull String to);
}
