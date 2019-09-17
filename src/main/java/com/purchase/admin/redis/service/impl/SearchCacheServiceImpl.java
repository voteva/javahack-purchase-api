package com.purchase.admin.redis.service.impl;

import com.purchase.admin.redis.domain.SearchResult;
import com.purchase.admin.redis.service.SearchCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Slf4j
@Service
public class SearchCacheServiceImpl
        implements SearchCacheService {

    private final RedisTemplate<String, SearchResult> redisTemplate;
    private final ListOperations<String, SearchResult> listOps;

    public SearchCacheServiceImpl(RedisTemplate<String, SearchResult> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOps = redisTemplate.opsForList();
    }

    @Override
    @SuppressWarnings("all")
    public boolean contains(@Nonnull String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.warn("Failed to extract key '{}'", key);
            return false;
        }
    }

    @Override
    public void save(@Nonnull String key, @Nonnull List<SearchResult> results) {
        results.forEach(result -> listOps.rightPush(key, result));
    }

    @Nonnull
    @Override
    @SuppressWarnings("all")
    public List<SearchResult> getValuesByKey(@Nonnull String key) {
        final List<SearchResult> results = newArrayList();
        while (listOps.size(key) > 0) {
            results.add(listOps.leftPop(key));
        }
        listOps.rightPushAll(key, results);
        return results;
    }
}
