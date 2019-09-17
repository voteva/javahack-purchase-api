package com.purchase.admin.service.impl;

import com.purchase.admin.config.properties.TwilioProperties;
import com.purchase.admin.domain.SearchHistory;
import com.purchase.admin.model.out.xml.GatherXml;
import com.purchase.admin.model.out.xml.SayXml;
import com.purchase.admin.model.out.xml.SpeechConfigXmlResponse;
import com.purchase.admin.service.HistoryService;
import com.purchase.admin.service.SpeechConfigService;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class SpeechConfigServiceImpl
        implements SpeechConfigService {

    private final TwilioProperties twilioProperties;
    private final HistoryService historyService;

    @Nonnull
    @Override
    public SpeechConfigXmlResponse getSpeechConfig(@Nonnull UUID supplierUid) {
        final SearchHistory query = historyService.getHistoryQueryBySupplierUid(supplierUid);

        final SayXml say = new SayXml();
        say.setValue(query.getMessage());

        final SpeechConfigXmlResponse response = new SpeechConfigXmlResponse();
        response.setSay(say);

        GatherXml gather = new GatherXml();
        gather.setAction(buildActionUrl(supplierUid));
        response.setGather(gather);

        return response;
    }

    @Nonnull
    private String buildActionUrl(@Nonnull UUID supplierUid) {
        final HttpUrl url = requireNonNull(HttpUrl.parse(twilioProperties.getRecordUrl()))
                .newBuilder()
                .addQueryParameter("supplierUid", supplierUid.toString())
                .build();
        return url.toString();
    }
}
