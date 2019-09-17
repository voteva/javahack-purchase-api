package com.purchase.admin.security;

import com.purchase.admin.domain.Client;
import com.purchase.admin.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;

@Component
@RequiredArgsConstructor
public class PhoneNumberAndPasswordAuthenticationManager {

    private final ClientRepository clientRepository;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

        final Optional<Client> clientOptional = clientRepository
                .findByPhoneAndPassword(
                        authenticationToken.getName(),
                        authenticationToken.getCredentials().toString());

        if (clientOptional.isEmpty()) {
            throw new WrongClientDataException("Unauthorized");
        }

        return new UsernamePasswordAuthenticationToken(clientOptional.get(), clientOptional.get().getPassword(), newHashSet());
    }
}
