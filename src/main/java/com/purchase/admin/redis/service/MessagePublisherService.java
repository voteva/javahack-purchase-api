package com.purchase.admin.redis.service;

import com.purchase.admin.redis.domain.SupplierCallTask;
import com.purchase.admin.redis.domain.SupplierEmailTask;

import javax.annotation.Nonnull;

public interface MessagePublisherService {

    void publish(@Nonnull SupplierCallTask task);

    void publish(@Nonnull SupplierEmailTask task);
}
