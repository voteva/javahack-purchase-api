package com.purchase.admin.security;

import com.purchase.admin.domain.Client;
import com.purchase.admin.domain.Session;
import com.purchase.admin.repository.ClientRepository;
import com.purchase.admin.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;
import static java.time.temporal.ChronoUnit.DAYS;

@Primary
@Component
@RequiredArgsConstructor
public class TokenAuthenticationClient
        implements AuthenticationManager {

    private final ClientRepository clientRepository;
    private final SessionRepository sessionRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<Session> sessionOptional = sessionRepository.findByToken(authentication.getName());

        Optional<Client> clientOptional;
        if (sessionOptional.isPresent()) {
            if (sessionOptional.get().getCreatedAt().isBefore(Instant.now().plus(2, DAYS))) {
                throw new WrongClientDataException("Unauthorized");
            }
            clientOptional = clientRepository.findClientBySessionsContains(sessionOptional.get());
        } else {
            throw new WrongClientDataException("Unauthorized");
        }

        if (clientOptional.isEmpty()) {
            throw new WrongClientDataException("Unauthorized");
        }

        return new UsernamePasswordAuthenticationToken(clientOptional.get(), clientOptional.get().getPassword(), newHashSet());
    }
}
