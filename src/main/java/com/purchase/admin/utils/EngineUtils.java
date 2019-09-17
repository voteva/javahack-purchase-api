package com.purchase.admin.utils;

import com.purchase.admin.config.properties.EngineProperties;
import com.purchase.admin.enumeration.Engine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.lang.String.format;

public class EngineUtils {

    @Nullable
    public static String getAttrBy(@Nonnull EngineProperties properties, @Nonnull Engine engine) {
        switch (engine) {
            case GOOGLE:
                return properties.getGoogleSearchAttr();
            case YANDEX:
                return properties.getYandexSearchAttr();
            case AVITO:
                return properties.getAvitoSearchAttr();
            default:
                return null;
        }
    }

    @Nullable
    public static String getSelectorBy(@Nonnull EngineProperties properties, @Nonnull Engine engine) {
        switch (engine) {
            case GOOGLE:
                return properties.getGoogleSearchSelector();
            case YANDEX:
                return properties.getYandexSearchSelector();
            case AVITO:
                return properties.getAvitoSearchSelector();
            default:
                return null;
        }
    }

    @Nullable
    public static String getSearchQuery(@Nonnull EngineProperties properties,
                                        @Nonnull Engine engine,
                                        @Nonnull String query) {
        switch (engine) {
            case GOOGLE:
                return format(properties.getGoogleSearchUrl(), query);
            case YANDEX:
                return format(properties.getYandexSearchUrl(), query);
            case AVITO:
                return format(properties.getAvitoSearchUrl(), query);
            default:
                return null;
        }
    }
}
