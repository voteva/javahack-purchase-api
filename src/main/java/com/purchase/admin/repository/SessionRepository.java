package com.purchase.admin.repository;

import com.purchase.admin.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface SessionRepository
        extends JpaRepository<Session, Long> {

    @Nonnull
    Optional<Session> findById(@Nonnull Long id);

    Optional<Session> findByToken(@Nonnull String token);

}