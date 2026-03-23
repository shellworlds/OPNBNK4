package com.bank.openbanking.repository;

import com.bank.openbanking.domain.Consent;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentRepository extends JpaRepository<Consent, UUID> {

    List<Consent> findByCustomerIdOrderByCreatedAtDesc(String customerId);
}
