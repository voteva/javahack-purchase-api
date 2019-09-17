package com.purchase.admin.service;

import com.purchase.admin.model.in.CallSupplierRequest;

import javax.annotation.Nonnull;
import java.util.List;

public interface SuppliersCallService {

    void call(@Nonnull List<CallSupplierRequest> requests, @Nonnull Long clientId);
}
