package com.bank.transaction.repository;

import com.bank.transaction.domain.TransactionEvent;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionEventRepository extends JpaRepository<TransactionEvent, UUID> {

    List<TransactionEvent> findTop100ByPublishedFalseOrderByCreatedAtAsc();
}
