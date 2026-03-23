package com.bank.openbanking.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentRequest(
        @NotNull UUID accountId,
        @NotNull @Positive BigDecimal amount,
        @NotBlank @Pattern(regexp = "CREDIT|DEBIT") String type,
        @NotNull @Size(min = 3, max = 3) String currency,
        @Size(max = 256) String reference) {}
