package com.purchase.admin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    @NotBlank
    private String host;

    @Min(0)
    private int port;

    @NotBlank
    private String password;
}
