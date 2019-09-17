package com.purchase.admin.service;

import com.purchase.admin.model.out.xml.SpeechConfigXmlResponse;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface SpeechConfigService {

    @Nonnull
    SpeechConfigXmlResponse getSpeechConfig(@Nonnull UUID supplierUid);
}
