package com.bank.openbanking.web.dto;

import com.bank.openbanking.domain.Consent;
import com.bank.openbanking.domain.ConsentStatus;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public record ConsentResponse(
        UUID consentId,
        String tppId,
        List<String> scopes,
        ConsentStatus status,
        String customerId,
        Instant createdAt,
        Instant validUntil) {

    public static ConsentResponse from(Consent c) {
        List<String> scopeList =
                c.getScopes() == null || c.getScopes().isBlank()
                        ? List.of()
                        : Arrays.stream(c.getScopes().split(",")).map(String::trim).toList();
        return new ConsentResponse(
                c.getId(), c.getTppId(), scopeList, c.getStatus(), c.getCustomerId(), c.getCreatedAt(), c.getValidUntil());
    }
}
