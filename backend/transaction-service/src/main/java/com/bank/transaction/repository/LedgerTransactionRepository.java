package com.bank.transaction.repository;

import com.bank.transaction.domain.LedgerTransaction;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerTransactionRepository extends JpaRepository<LedgerTransaction, UUID> {

    List<LedgerTransaction> findByAccountIdOrderByBookedAtDesc(UUID accountId);
}
