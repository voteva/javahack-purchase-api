package com.purchase.admin.web;

import com.purchase.admin.model.in.SearchRequest;
import com.purchase.admin.model.out.SearchResponse;
import com.purchase.admin.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {
    private static final Long CLIENT_ID = 1L;

    private final SearchService searchService;

    @PostMapping(
            consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE
    )
    public SearchResponse search(@RequestBody @Valid SearchRequest request) {
        return searchService.search(request, CLIENT_ID);
    }
}
