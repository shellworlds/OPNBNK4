package com.bank.account.web.dto;

import com.bank.account.domain.BankAccount;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AccountResponse(
        UUID id, String iban, String currency, BigDecimal balance, String customerId, Instant createdAt) {

    public static AccountResponse from(BankAccount a) {
        return new AccountResponse(
                a.getId(), a.getIban(), a.getCurrency(), a.getBalance(), a.getCustomerId(), a.getCreatedAt());
    }
}
