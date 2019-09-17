package com.purchase.admin.redis.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.purchase.admin.redis.domain.RedisTask;
import com.purchase.admin.redis.service.MessageSubscriberService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.apache.http.util.TextUtils.isEmpty;

@Service
public class MessageSubscriberServiceImpl
        implements MessageSubscriberService {

    private final Jedis jedis;
    private final Gson gson;

    public MessageSubscriberServiceImpl(Jedis jedis) {
        this.jedis = jedis;
        this.gson = new GsonBuilder().create();
    }

    @Nullable
    @Override
    public <T extends RedisTask> T getOne(@Nonnull String topic, Class<T> taskClass) {
        final String result = jedis.rpop(topic);
        if (isEmpty(result)) {
            return null;
        }
        return gson.fromJson(result, taskClass);
    }
}
