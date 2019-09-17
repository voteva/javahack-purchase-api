package com.purchase.admin.web;

import com.purchase.admin.model.in.UpdateSettingsRequest;
import com.purchase.admin.model.out.ClientSettingsResponse;
import com.purchase.admin.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings")
public class SettingsController {
    private static final Long CLIENT_ID = 1L;

    private final ClientService clientService;

    @GetMapping(
            produces = APPLICATION_JSON_UTF8_VALUE
    )
    public ClientSettingsResponse getSettings() {
        return clientService.getSettings(CLIENT_ID);
    }

    @PutMapping(
            consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE
    )
    public ClientSettingsResponse updateSettings(@RequestBody @Valid UpdateSettingsRequest request) {
        return clientService.updateSettings(request, CLIENT_ID);
    }
}
