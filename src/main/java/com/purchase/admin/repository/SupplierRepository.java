package com.purchase.admin.repository;

import com.purchase.admin.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository
        extends JpaRepository<Supplier, Long> {

    @Nonnull
    Optional<Supplier> findBySupplierUid(@Nonnull UUID supplierUid);
}
