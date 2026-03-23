package com.bank.transaction.web.dto;

import com.bank.transaction.domain.TransactionMovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransactionRequest(
        @NotNull UUID accountId,
        @NotNull @Positive BigDecimal amount,
        @NotNull TransactionMovementType type,
        @NotNull @Size(min = 3, max = 3) String currency,
        @Size(max = 512) String description,
        @Size(max = 256) String reference) {}
