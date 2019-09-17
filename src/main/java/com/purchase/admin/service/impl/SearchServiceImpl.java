package com.purchase.admin.service.impl;

import com.purchase.admin.domain.Client;
import com.purchase.admin.domain.SearchHistory;
import com.purchase.admin.domain.Supplier;
import com.purchase.admin.model.in.SearchRequest;
import com.purchase.admin.model.out.SearchResponse;
import com.purchase.admin.model.out.SupplierInfoResponse;
import com.purchase.admin.redis.service.SearchCacheService;
import com.purchase.admin.redis.domain.SearchResult;
import com.purchase.admin.service.ClientService;
import com.purchase.admin.service.HistoryService;
import com.purchase.admin.service.ParsingService;
import com.purchase.admin.service.SearchService;
import com.purchase.admin.service.SuppliersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl
        implements SearchService {

    private final ClientService clientService;
    private final SuppliersService suppliersService;
    private final HistoryService historyService;
    private final ParsingService parsingService;
    private final SearchCacheService searchCacheService;

    @Nonnull
    @Override
    @Transactional
    public SearchResponse search(@Nonnull SearchRequest request, @Nonnull Long clientId) {
        final Client client = clientService.getClient(clientId);

        final SearchHistory history = buildSearchHistory(client, request);
        historyService.save(history);

        final List<SupplierInfoResponse> suppliers = search(request);
        final List<Supplier> savedSuppliers = suppliersService.saveSuppliers(suppliers, history);

        return buildSearchResponse(request, history, savedSuppliers);
    }

    @Nonnull
    private List<SupplierInfoResponse> search(@Nonnull SearchRequest request) {
        List<SupplierInfoResponse> suppliers;
        if (searchCacheService.contains(request.getQueryString())) {
            suppliers = searchCacheService
                    .getValuesByKey(request.getQueryString())
                    .stream()
                    .map(this::buildSupplierInfoResponse)
                    .collect(Collectors.toList());
        } else {
            suppliers = parsingService.parseSuppliers(request);
            final List<SearchResult> searchResults = suppliers.stream()
                    .map(this::buildSearchResult)
                    .collect(Collectors.toList());
            searchCacheService.save(request.getQueryString(), searchResults);
        }
        return suppliers;
    }

    @Nonnull
    private SearchHistory buildSearchHistory(@Nonnull Client client,
                                             @Nonnull SearchRequest request) {
        return new SearchHistory()
                .setQueryUid(UUID.randomUUID())
                .setClient(client)
                .setQueryString(request.getQueryString())
                .setMessage(request.getMessage());
    }

    @Nonnull
    private SearchResponse buildSearchResponse(@Nonnull SearchRequest request,
                                               @Nonnull SearchHistory history,
                                               @Nonnull List<Supplier> suppliers) {
        final List<SupplierInfoResponse> suppliersInfo = suppliers.stream()
                .map(this::buildSupplierInfoResponse)
                .collect(Collectors.toList());

        return new SearchResponse()
                .setQueryUid(history.getQueryUid())
                .setQueryString(request.getQueryString())
                .setEngines(request.getEngines())
                .setContactTypes(request.getContactTypes())
                .setSuppliers(suppliersInfo);
    }

    @Nonnull
    private SupplierInfoResponse buildSupplierInfoResponse(@Nonnull Supplier supplier) {
        return new SupplierInfoResponse()
                .setSupplierUid(supplier.getSupplierUid())
                .setStatus(supplier.getStatus())
                .setName(supplier.getName())
                .setUrl(supplier.getUrl())
                .setEmail(supplier.getEmail())
                .setPhone(supplier.getPhone());
    }

    @Nonnull
    private SupplierInfoResponse buildSupplierInfoResponse(@Nonnull SearchResult searchResult) {
        return new SupplierInfoResponse()
                .setName(searchResult.getSiteName())
                .setUrl(searchResult.getUrl())
                .setPhone(searchResult.getPhone())
                .setEmail(searchResult.getEmail());
    }

    @Nonnull
    private SearchResult buildSearchResult(@Nonnull SupplierInfoResponse supplierInfo) {
        return new SearchResult()
                .setSiteName(supplierInfo.getName())
                .setEmail(supplierInfo.getEmail())
                .setPhone(supplierInfo.getPhone())
                .setUrl(supplierInfo.getUrl());
    }
}
