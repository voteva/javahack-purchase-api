package com.purchase.admin.web;

import com.purchase.admin.domain.Client;
import com.purchase.admin.domain.Session;
import com.purchase.admin.model.in.AuthenticationRequest;
import com.purchase.admin.model.out.AuthenticationResponse;
import com.purchase.admin.service.SessionService;
import com.purchase.admin.security.PhoneNumberAndPasswordAuthenticationManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final PhoneNumberAndPasswordAuthenticationManager authenticationManager;
    private final SessionService sessionService;

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest request) {
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword()));
        final Client client = (Client) authentication.getPrincipal();
        final Session session = sessionService.createSession(client);
        return new AuthenticationResponse(session.getToken());
    }
}
