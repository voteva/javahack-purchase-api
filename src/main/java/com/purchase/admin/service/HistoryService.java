package com.purchase.admin.service;

import com.purchase.admin.domain.SearchHistory;
import com.purchase.admin.model.out.QueryInfoResponse;
import com.purchase.admin.model.out.SupplierInfoResponse;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public interface HistoryService {

    @Nonnull
    SearchHistory getHistoryQueryBySupplierUid(@Nonnull UUID supplierUid);

    @Nonnull
    List<QueryInfoResponse> getQueries(@Nonnull Long clientId);

    @Nonnull
    QueryInfoResponse getQuery(@Nonnull UUID queryUid, @Nonnull Long clientId);

    @Nonnull
    List<SupplierInfoResponse> getQuerySuppliers(@Nonnull UUID queryUid, @Nonnull Long clientId);

    @Nonnull
    SearchHistory save(@Nonnull SearchHistory history);
}
