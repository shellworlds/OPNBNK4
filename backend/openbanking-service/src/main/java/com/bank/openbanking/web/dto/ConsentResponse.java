package com.bank.openbanking.web.dto;

import com.bank.openbanking.domain.Consent;
import com.bank.openbanking.domain.ConsentStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ConsentResponse(
        UUID consentId,
        String customerId,
        String tppId,
        List<String> permissions,
        ConsentStatus status,
        Instant validFrom,
        Instant validTo,
        Instant createdAt) {

    public static ConsentResponse from(Consent c, ObjectMapper mapper) {
        List<String> perms;
        try {
            perms = mapper.readValue(c.getPermissions(), new TypeReference<>() {});
        } catch (Exception e) {
            perms = List.of();
        }
        return new ConsentResponse(
                c.getId(), c.getCustomerId(), c.getTppExternalId(), perms, c.getStatus(), c.getValidFrom(), c.getValidTo(), c.getCreatedAt());
    }
}
