package com.purchase.admin.redis.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.purchase.admin.config.properties.TopicsProperties;
import com.purchase.admin.redis.domain.SupplierCallTask;
import com.purchase.admin.redis.domain.SupplierEmailTask;
import com.purchase.admin.redis.service.MessagePublisherService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Nonnull;

@Service
public class MessagePublisherServiceImpl
        implements MessagePublisherService {

    private final TopicsProperties topicsProperties;
    private final Jedis jedis;
    private final Gson gson;

    public MessagePublisherServiceImpl(Jedis jedis, TopicsProperties topicsProperties) {
        this.jedis = jedis;
        this.topicsProperties = topicsProperties;
        this.gson = new GsonBuilder().create();
    }

    @Override
    public void publish(@Nonnull SupplierCallTask task) {
        jedis.lpush(topicsProperties.getSupplierCallTopic(), gson.toJson(task));
    }

    @Override
    public void publish(@Nonnull SupplierEmailTask task) {
        jedis.lpush(topicsProperties.getSupplierEmailTopic(), gson.toJson(task));
    }
}
