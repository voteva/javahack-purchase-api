package com.purchase.admin.service;

import com.purchase.admin.domain.ApplicationSettings;

import javax.annotation.Nonnull;

public interface ApplicationSettingsService {

    @Nonnull
    ApplicationSettings loadSettings();
}
