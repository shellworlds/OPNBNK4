package com.bank.openbanking.repository;

import com.bank.openbanking.domain.Consent;
import com.bank.openbanking.domain.ConsentStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentRepository extends JpaRepository<Consent, UUID> {

    Optional<Consent> findFirstByTppExternalIdAndCustomerIdAndStatusOrderByCreatedAtDesc(
            String tppExternalId, String customerId, ConsentStatus status);
}
