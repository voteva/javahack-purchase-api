package com.purchase.admin.web;

import com.purchase.admin.model.in.CallSupplierRequest;
import com.purchase.admin.service.SuppliersCallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/suppliers/call")
public class SuppliersCallController {
    private static final Long CLIENT_ID = 1L;

    private final SuppliersCallService suppliersCallService;

    @PostMapping(
            consumes = APPLICATION_JSON_UTF8_VALUE
    )
    public void call(@RequestBody @Valid List<CallSupplierRequest> requests) {
        suppliersCallService.call(requests, CLIENT_ID);
    }
}
