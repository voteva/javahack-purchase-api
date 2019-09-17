package com.purchase.admin.service.impl;

import com.purchase.admin.config.properties.TwilioProperties;
import com.purchase.admin.service.CallService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallServiceImpl
        implements CallService {

    private final TwilioProperties twilioProperties;

    @Override
    public void call(@Nonnull UUID supplierUid, @Nonnull String phoneNumber) {
        try {
            Twilio.init(twilioProperties.getUsername(), twilioProperties.getPassword());

            Call.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioProperties.getPhoneNumber()),
                    new URI(buildRedirectUrl(supplierUid)))
                    .create();
        } catch (Exception e) {
            log.warn("Failed to produce call to '{}'", phoneNumber);
        }
    }

    @Nonnull
    private String buildRedirectUrl(@Nonnull UUID supplierUid) {
        final HttpUrl url = requireNonNull(HttpUrl.parse(twilioProperties.getRedirectUrl()))
                .newBuilder()
                .addQueryParameter("supplierUid", supplierUid.toString())
                .build();
        return url.toString();
    }
}
