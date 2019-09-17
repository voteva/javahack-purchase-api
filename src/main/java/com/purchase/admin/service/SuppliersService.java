package com.purchase.admin.service;

import com.purchase.admin.domain.SearchHistory;
import com.purchase.admin.domain.Supplier;
import com.purchase.admin.enumeration.ContactType;
import com.purchase.admin.model.out.SupplierInfoResponse;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public interface SuppliersService {

    @Nonnull
    List<Supplier> saveSuppliers(@Nonnull List<SupplierInfoResponse> suppliers, @Nonnull SearchHistory history);

    @Nonnull
    Supplier getSupplier(@Nonnull UUID supplierUid);

    void saveSupplierAnswer(@Nonnull UUID supplierUid, @Nonnull String text, @Nonnull ContactType contactType);
}
