package com.purchase.admin.repository;

import com.purchase.admin.domain.PurchaseEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailRepository
        extends JpaRepository<PurchaseEmail, Long> {

    Optional<PurchaseEmail> findByEmailFromAndEmailToAndContentAndSubject(String emailFrom, String emailTo, String content, String subject);
    List<PurchaseEmail> findByClientIdAndEmailTo(Long clientId, String emailTo);
    List<PurchaseEmail> findByClientIdAndEmailToContains(Long clientId, String emailTo);
}
