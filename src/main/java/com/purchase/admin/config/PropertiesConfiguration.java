package com.purchase.admin.config;

import com.purchase.admin.config.properties.EngineProperties;
import com.purchase.admin.config.properties.MailProperties;
import com.purchase.admin.config.properties.RedisProperties;
import com.purchase.admin.config.properties.TopicsProperties;
import com.purchase.admin.config.properties.TwilioProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        EngineProperties.class,
        MailProperties.class,
        RedisProperties.class,
        TopicsProperties.class,
        TwilioProperties.class
})
public class PropertiesConfiguration {
}
