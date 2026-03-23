package com.bank.transaction.web.dto;

import com.bank.transaction.domain.Transaction;
import com.bank.transaction.domain.TransactionMovementType;
import com.bank.transaction.domain.TransactionStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        UUID accountId,
        BigDecimal amount,
        String currency,
        TransactionMovementType type,
        String description,
        String reference,
        TransactionStatus status,
        Instant createdAt) {

    public static TransactionResponse from(Transaction t) {
        return new TransactionResponse(
                t.getId(),
                t.getAccountId(),
                t.getAmount(),
                t.getCurrency(),
                t.getType(),
                t.getDescription(),
                t.getReference(),
                t.getStatus(),
                t.getCreatedAt());
    }
}
