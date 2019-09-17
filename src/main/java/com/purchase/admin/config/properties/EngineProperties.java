package com.purchase.admin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties(prefix = "engine")
public class EngineProperties {

    @NotBlank
    private String googleSearchUrl;
    @NotBlank
    private String yandexSearchUrl;
    @NotBlank
    private String avitoSearchUrl;

    @NotBlank
    private String googleSearchAttr;
    @NotBlank
    private String yandexSearchAttr;
    @NotBlank
    private String avitoSearchAttr;

    @NotBlank
    private String googleSearchSelector;
    @NotBlank
    private String yandexSearchSelector;
    @NotBlank
    private String avitoSearchSelector;
}
