package com.purchase.admin.service.impl;

import com.purchase.admin.domain.Client;
import com.purchase.admin.domain.SearchHistory;
import com.purchase.admin.domain.Supplier;
import com.purchase.admin.domain.SupplierAnswer;
import com.purchase.admin.model.out.QueryInfoResponse;
import com.purchase.admin.model.out.SupplierAnswerResponse;
import com.purchase.admin.model.out.SupplierInfoResponse;
import com.purchase.admin.repository.SearchHistoryRepository;
import com.purchase.admin.service.ClientService;
import com.purchase.admin.service.HistoryService;
import com.purchase.admin.service.SuppliersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.purchase.admin.utils.DateTimeUtils.formatDateTime;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl
        implements HistoryService {

    private final ClientService clientService;
    private final SuppliersService suppliersService;
    private final SearchHistoryRepository searchHistoryRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public SearchHistory getHistoryQueryBySupplierUid(@Nonnull UUID supplierUid) {
        final Supplier supplier = suppliersService.getSupplier(supplierUid);
        return supplier.getSearchHistory();
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<QueryInfoResponse> getQueries(@Nonnull Long clientId) {
        final Client client = clientService.getClient(clientId);
        final List<SearchHistory> searchHistoryList = Optional
                .ofNullable(client.getSearchHistory())
                .orElse(newArrayList());

        return searchHistoryList.stream()
                .sorted(Comparator.comparing(SearchHistory::getCreatedDate))
                .map(this::buildQueryInfoResponse)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public QueryInfoResponse getQuery(@Nonnull UUID queryUid, @Nonnull Long clientId) {
        final Client client = clientService.getClient(clientId);
        return searchHistoryRepository
                .findByQueryUidAndClientId(queryUid, client.getId())
                .map(this::buildQueryInfoResponse)
                .orElseThrow(() -> new EntityNotFoundException(format("Query not found with uid '%s'", queryUid)));
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<SupplierInfoResponse> getQuerySuppliers(@Nonnull UUID queryUid, @Nonnull Long clientId) {
        final Client client = clientService.getClient(clientId);

        final SearchHistory searchHistory = searchHistoryRepository
                .findByQueryUidAndClientId(queryUid, client.getId())
                .orElseThrow(() -> new EntityNotFoundException(format("Query not found with uid '%s'", queryUid)));

        final List<Supplier> suppliers = Optional
                .ofNullable(searchHistory.getSuppliers())
                .orElse(newArrayList());

        return suppliers.stream()
                .map(this::buildSupplierInfoResponse)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    @Transactional
    public SearchHistory save(@Nonnull SearchHistory history) {
        return searchHistoryRepository.save(history);
    }

    @Nonnull
    private QueryInfoResponse buildQueryInfoResponse(@Nonnull SearchHistory searchHistory) {
        return new QueryInfoResponse()
                .setQueryUid(searchHistory.getQueryUid())
                .setQueryString(searchHistory.getQueryString())
                .setCreatedDate(formatDateTime(searchHistory.getCreatedDate()));
    }

    @Nonnull
    private SupplierInfoResponse buildSupplierInfoResponse(@Nonnull Supplier supplier) {
        final List<SupplierAnswer> answers = Optional
                .ofNullable(supplier.getAnswers())
                .orElse(newArrayList());

        final List<SupplierAnswerResponse> answersResponse = answers.stream()
                .map(this::buildSupplierAnswerResponse)
                .collect(Collectors.toList());

        return new SupplierInfoResponse()
                .setSupplierUid(supplier.getSupplierUid())
                .setName(supplier.getName())
                .setUrl(supplier.getUrl())
                .setEmail(supplier.getEmail())
                .setPhone(supplier.getPhone())
                .setStatus(supplier.getStatus())
                .setAnswers(answersResponse);
    }

    @Nonnull
    private SupplierAnswerResponse buildSupplierAnswerResponse(@Nonnull SupplierAnswer answer) {
        return new SupplierAnswerResponse()
                .setContactType(answer.getType())
                .setMessage(answer.getMessage());
    }
}
