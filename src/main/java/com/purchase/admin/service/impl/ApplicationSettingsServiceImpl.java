package com.purchase.admin.service.impl;

import com.purchase.admin.domain.ApplicationSettings;
import com.purchase.admin.repository.ApplicationSettingsRepository;
import com.purchase.admin.service.ApplicationSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ApplicationSettingsServiceImpl
        implements ApplicationSettingsService {

    private final ApplicationSettingsRepository applicationSettingsRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public ApplicationSettings loadSettings() {
        return applicationSettingsRepository
                .findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No application settings found"));
    }
}
