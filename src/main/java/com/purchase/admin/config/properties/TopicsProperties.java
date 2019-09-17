package com.purchase.admin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties(prefix = "redis.topics")
public class TopicsProperties {

    @NotBlank
    private String supplierCallTopic;

    @NotBlank
    private String supplierEmailTopic;
}
