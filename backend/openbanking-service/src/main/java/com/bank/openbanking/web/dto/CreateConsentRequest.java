package com.bank.openbanking.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

public record CreateConsentRequest(
        @NotBlank String customerId,
        @NotBlank String tppId,
        @JsonProperty("permissions") List<String> permissions,
        @JsonProperty("scopes") List<String> scopes,
        Instant validFrom,
        @JsonAlias("validUntil") Instant validTo) {

    public List<String> resolvedPermissions() {
        if (permissions != null && !permissions.isEmpty()) {
            return permissions;
        }
        if (scopes != null && !scopes.isEmpty()) {
            return scopes;
        }
        return List.of();
    }
}
