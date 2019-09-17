package com.purchase.admin.service.impl;

import com.purchase.admin.domain.SearchHistory;
import com.purchase.admin.domain.Supplier;
import com.purchase.admin.domain.SupplierAnswer;
import com.purchase.admin.enumeration.ContactType;
import com.purchase.admin.enumeration.SupplierStatus;
import com.purchase.admin.model.out.SupplierInfoResponse;
import com.purchase.admin.repository.SupplierAnswerRepository;
import com.purchase.admin.repository.SupplierRepository;
import com.purchase.admin.service.SuppliersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.purchase.admin.enumeration.SupplierStatus.OK_ITEM;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class SuppliersServiceImpl
        implements SuppliersService {

    private final SupplierRepository supplierRepository;
    private final SupplierAnswerRepository supplierAnswerRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public Supplier getSupplier(@Nonnull UUID supplierUid) {
        return supplierRepository
                .findBySupplierUid(supplierUid)
                .orElseThrow(() -> new EntityNotFoundException(format("Supplier not found by uid '%s'", supplierUid)));
    }

    @Nonnull
    @Override
    @Transactional
    public List<Supplier> saveSuppliers(@Nonnull List<SupplierInfoResponse> suppliersInfo, @Nonnull SearchHistory history) {
        final List<Supplier> suppliers = suppliersInfo.stream()
                .map(s -> buildSupplier(s, history))
                .collect(Collectors.toList());

        return supplierRepository.saveAll(suppliers);
    }

    @Override
    public void saveSupplierAnswer(@Nonnull UUID supplierUid, @Nonnull String text, @Nonnull ContactType contactType) {
        final Supplier supplier = getSupplier(supplierUid);
        supplier.setStatus(OK_ITEM);
        supplierRepository.save(supplier);

        final SupplierAnswer answer = buildSupplierAnswer(supplier, contactType, text);
        supplierAnswerRepository.save(answer);
    }

    @Nonnull
    private Supplier buildSupplier(@Nonnull SupplierInfoResponse supplier, @Nonnull SearchHistory history) {
        return new Supplier()
                .setSupplierUid(UUID.randomUUID())
                .setName(supplier.getName())
                .setUrl(supplier.getUrl())
                .setPhone(supplier.getPhone())
                .setEmail(supplier.getEmail())
                .setStatus(SupplierStatus.NO_RESPONSE)
                .setSearchHistory(history);
    }

    @Nonnull
    private SupplierAnswer buildSupplierAnswer(@Nonnull Supplier supplier,
                                               @Nonnull ContactType contactType,
                                               @Nonnull String text) {
        return new SupplierAnswer()
                .setAnswerUid(UUID.randomUUID())
                .setSupplier(supplier)
                .setType(contactType)
                .setMessage(text);
    }
}
