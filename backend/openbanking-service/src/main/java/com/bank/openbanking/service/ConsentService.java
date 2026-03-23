package com.bank.openbanking.service;

import com.bank.openbanking.domain.Consent;
import com.bank.openbanking.domain.ConsentStatus;
import com.bank.openbanking.repository.ConsentRepository;
import com.bank.openbanking.repository.TppRepository;
import com.bank.openbanking.web.dto.ConsentResponse;
import com.bank.openbanking.web.dto.CreateConsentRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ConsentService {

    private final ConsentRepository consents;
    private final TppRepository tpps;
    private final ObjectMapper objectMapper;

    public ConsentService(ConsentRepository consents, TppRepository tpps, ObjectMapper objectMapper) {
        this.consents = consents;
        this.tpps = tpps;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public ConsentResponse getById(UUID id) {
        return consents
                .findById(id)
                .map(c -> ConsentResponse.from(c, objectMapper))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consent not found"));
    }

    @Transactional
    public ConsentResponse create(CreateConsentRequest request) {
        List<String> perms = request.resolvedPermissions();
        if (perms.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "permissions or scopes required");
        }
        tpps.findByExternalId(request.tppId().trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown TPP"));
        String json;
        try {
            json = objectMapper.writeValueAsString(perms);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        var entity = new Consent(
                request.customerId().trim(), request.tppId().trim(), json, request.validFrom(), request.validTo());
        return ConsentResponse.from(consents.save(entity), objectMapper);
    }

    @Transactional
    public void revoke(UUID id) {
        Consent c =
                consents.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consent not found"));
        c.setStatus(ConsentStatus.REVOKED);
        consents.save(c);
    }

    @Transactional(readOnly = true)
    public void assertActiveConsent(String tppExternalId, String customerId, String requiredScope) {
        Consent c = consents
                .findFirstByTppExternalIdAndCustomerIdAndStatusOrderByCreatedAtDesc(
                        tppExternalId, customerId, ConsentStatus.ACTIVE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "No active consent"));
        if (c.getValidTo() != null && Instant.now().isAfter(c.getValidTo())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Consent expired");
        }
        List<String> perms;
        try {
            perms = objectMapper.readValue(c.getPermissions(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid consent permissions");
        }
        if (!perms.contains(requiredScope)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Missing scope: " + requiredScope);
        }
    }
}
