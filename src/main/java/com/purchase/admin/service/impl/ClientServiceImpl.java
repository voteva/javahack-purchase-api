package com.purchase.admin.service.impl;

import com.purchase.admin.domain.Client;
import com.purchase.admin.domain.Session;
import com.purchase.admin.model.in.UpdateSettingsRequest;
import com.purchase.admin.model.out.ClientSettingsResponse;
import com.purchase.admin.repository.ClientRepository;
import com.purchase.admin.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl
        implements ClientService {

    private final ClientRepository clientRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public Client getClient(@Nonnull Long clientId) {
        return clientRepository
                .findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client does not exists"));
    }

    @Nonnull
    @Override
    public Client findClientByUid(@Nonnull UUID clientUid) {
        return clientRepository
                .findByClientUid(clientUid)
                .orElseThrow(() -> new EntityNotFoundException("Client does not exists"));
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public ClientSettingsResponse getSettings(@Nonnull Long clientId) {
        final Client client = getClient(clientId);
        return buildClientSettingsResponse(client);
    }

    @Nonnull
    @Override
    @Transactional
    public ClientSettingsResponse updateSettings(@Nonnull UpdateSettingsRequest request, @Nonnull Long clientId) {
        final Client client = getClient(clientId)
                .setEmail(request.getEmail())
                .setPhone(request.getPhone());

        clientRepository.save(client);

        return buildClientSettingsResponse(client);
    }

    @Nonnull
    private ClientSettingsResponse buildClientSettingsResponse(@Nonnull Client client) {
        return new ClientSettingsResponse()
                .setEmail(client.getEmail())
                .setPhone(client.getPhone());
    }

    @Nonnull
    @Override
    public Session createSession(Client client,
                                 Session session) {
        session.setClient(client);
        client.getSessions().add(session);
        clientRepository.save(client);
        return session;
    }
}
