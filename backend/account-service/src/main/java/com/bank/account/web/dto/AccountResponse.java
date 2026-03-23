package com.bank.account.web.dto;

import com.bank.account.domain.Account;
import com.bank.account.domain.AccountStatus;
import com.bank.account.domain.AccountType;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String customerId,
        String accountNumber,
        AccountType accountType,
        String currency,
        BigDecimal balance,
        AccountStatus status,
        Instant createdAt,
        Long version) {

    public static AccountResponse from(Account a) {
        return new AccountResponse(
                a.getId(),
                a.getCustomerId(),
                a.getAccountNumber(),
                a.getAccountType(),
                a.getCurrency(),
                a.getBalance(),
                a.getStatus(),
                a.getCreatedAt(),
                a.getVersion());
    }
}
