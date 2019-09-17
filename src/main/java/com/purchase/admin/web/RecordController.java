package com.purchase.admin.web;

import com.purchase.admin.enumeration.ContactType;
import com.purchase.admin.service.SuppliersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/record")
public class RecordController {
    private static final String URL_ENCODED_VALUE = "application/x-www-form-urlencoded;charset=utf-8";

    private final SuppliersService suppliersService;

    @PostMapping(
            consumes = URL_ENCODED_VALUE
    )
    public void receiveRecord(@RequestParam UUID supplierUid,
                              @RequestParam(name = "SpeechResult") String speechResult) {
        suppliersService.saveSupplierAnswer(supplierUid, speechResult, ContactType.CALL);
    }
}
