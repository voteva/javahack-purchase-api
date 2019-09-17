package com.purchase.admin.repository;

import com.purchase.admin.domain.Client;
import com.purchase.admin.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository
        extends JpaRepository<Client, Long> {

    @Nonnull
    Optional<Client> findById(@Nonnull Long id);

    @Nonnull
    Optional<Client> findByPhone(@Nonnull String phone);

    @Nonnull
    Optional<Client> findByPhoneAndPassword(@Nonnull String phone, @Nonnull String password);

    @Nonnull
    Optional<Client> findClientBySessionsContains(@Nonnull Session session);

    @Nonnull
    Optional<Client> findByClientUid(@Nonnull UUID clientUid);
}
