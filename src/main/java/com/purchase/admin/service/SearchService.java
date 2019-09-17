package com.purchase.admin.service;

import com.purchase.admin.model.in.SearchRequest;
import com.purchase.admin.model.out.SearchResponse;

import javax.annotation.Nonnull;

public interface SearchService {

    @Nonnull
    SearchResponse search(@Nonnull SearchRequest request, @Nonnull Long clientId);
}
