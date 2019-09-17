package com.purchase.admin.redis.service;

import com.purchase.admin.redis.domain.RedisTask;

import javax.annotation.Nonnull;

public interface MessageSubscriberService {

    <T extends RedisTask> T getOne(@Nonnull String topic, Class<T> taskClass);
}
