package com.bank.openbanking.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

public record CreateConsentRequest(
        @NotBlank @Size(max = 128) String tppId,
        @NotEmpty List<@NotBlank @Size(max = 64) String> scopes,
        @NotBlank @Size(max = 128) String customerId,
        Instant validUntil) {}
