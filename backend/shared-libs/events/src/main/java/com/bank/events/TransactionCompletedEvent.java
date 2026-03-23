package com.bank.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransactionCompletedEvent(
        UUID transactionId,
        UUID accountId,
        BigDecimal amount,
        String type,
        String currency,
        String reference) {}
