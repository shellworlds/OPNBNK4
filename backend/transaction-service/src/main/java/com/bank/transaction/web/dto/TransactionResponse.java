package com.bank.transaction.web.dto;

import com.bank.transaction.domain.LedgerTransaction;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionResponse(
        UUID id, UUID accountId, BigDecimal amount, String currency, String reference, Instant bookedAt) {

    public static TransactionResponse from(LedgerTransaction t) {
        return new TransactionResponse(
                t.getId(), t.getAccountId(), t.getAmount(), t.getCurrency(), t.getReference(), t.getBookedAt());
    }
}
