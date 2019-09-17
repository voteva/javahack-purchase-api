package com.purchase.admin.service;

import com.purchase.admin.domain.Client;
import com.purchase.admin.domain.Session;
import com.purchase.admin.model.in.UpdateSettingsRequest;
import com.purchase.admin.model.out.ClientSettingsResponse;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface ClientService {

    @Nonnull
    Client getClient(@Nonnull Long clientId);

    @Nonnull
    Client findClientByUid(@Nonnull UUID clientUid);

    @Nonnull
    ClientSettingsResponse getSettings(@Nonnull Long clientId);

    @Nonnull
    ClientSettingsResponse updateSettings(@Nonnull UpdateSettingsRequest request, @Nonnull Long clientId);

    @Nonnull
    Session createSession(Client managerAccount, Session session);
}
