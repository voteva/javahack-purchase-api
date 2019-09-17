package com.purchase.admin.service.impl;

import com.purchase.admin.domain.Client;
import com.purchase.admin.domain.Session;
import com.purchase.admin.service.ClientService;
import com.purchase.admin.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl
        implements SessionService {

    private final ClientService clientService;

    @Override
    @Transactional
    public Session createSession(Client client) {
        final String token = UUID.randomUUID().toString();
        Session session = new Session();
        session.setToken(token);
        return clientService.createSession(client, session);
    }
}
