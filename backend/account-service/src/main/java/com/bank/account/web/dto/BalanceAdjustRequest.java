package com.bank.account.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record BalanceAdjustRequest(
        @NotNull @Positive BigDecimal amount,
        @NotBlank String type, // CREDIT or DEBIT
        @NotBlank String currency) {}
