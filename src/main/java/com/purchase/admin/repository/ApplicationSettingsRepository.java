package com.purchase.admin.repository;

import com.purchase.admin.domain.ApplicationSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationSettingsRepository
        extends JpaRepository<ApplicationSettings, Long> {
}
