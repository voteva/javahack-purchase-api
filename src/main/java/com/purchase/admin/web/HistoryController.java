package com.purchase.admin.web;

import com.purchase.admin.model.out.QueryInfoResponse;
import com.purchase.admin.model.out.SupplierInfoResponse;
import com.purchase.admin.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
public class HistoryController {
    private static final Long CLIENT_ID = 1L;

    private final HistoryService historyService;

    @GetMapping(
            value = "/queries",
            produces = APPLICATION_JSON_UTF8_VALUE
    )
    public List<QueryInfoResponse> getQueries() {
        return historyService.getQueries(CLIENT_ID);
    }

    @GetMapping(
            value = "/queries/{queryUid}",
            produces = APPLICATION_JSON_UTF8_VALUE
    )
    public QueryInfoResponse getQuery(@PathVariable UUID queryUid) {
        return historyService.getQuery(queryUid, CLIENT_ID);
    }

    @GetMapping(
            value = "/queries/{queryUid}/suppliers",
            produces = APPLICATION_JSON_UTF8_VALUE
    )
    public List<SupplierInfoResponse> getQuerySuppliers(@PathVariable UUID queryUid) {
        return historyService.getQuerySuppliers(queryUid, CLIENT_ID);
    }
}
