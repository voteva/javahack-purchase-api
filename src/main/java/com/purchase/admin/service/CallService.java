package com.purchase.admin.service;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface CallService {

    void call(@Nonnull UUID supplierUid, @Nonnull String phoneNumber);
}
