package com.purchase.admin.redis.service;

import com.purchase.admin.redis.domain.SearchResult;

import javax.annotation.Nonnull;
import java.util.List;

public interface SearchCacheService {

    boolean contains(@Nonnull String key);

    void save(@Nonnull String key, @Nonnull List<SearchResult> results);

    @Nonnull
    List<SearchResult> getValuesByKey(@Nonnull String key);
}
