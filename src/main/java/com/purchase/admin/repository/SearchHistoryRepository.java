package com.purchase.admin.repository;

import com.purchase.admin.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public interface SearchHistoryRepository
        extends JpaRepository<SearchHistory, Long> {

    @Nonnull
    Optional<SearchHistory> findByQueryUidAndClientId(@Nonnull UUID queryUid, @Nonnull Long clientId);
}
