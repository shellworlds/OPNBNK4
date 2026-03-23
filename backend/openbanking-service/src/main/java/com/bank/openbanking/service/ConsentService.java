package com.bank.openbanking.service;

import com.bank.openbanking.domain.Consent;
import com.bank.openbanking.domain.ConsentStatus;
import com.bank.openbanking.repository.ConsentRepository;
import com.bank.openbanking.web.dto.ConsentResponse;
import com.bank.openbanking.web.dto.CreateConsentRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ConsentService {

    private final ConsentRepository consents;

    public ConsentService(ConsentRepository consents) {
        this.consents = consents;
    }

    @Transactional(readOnly = true)
    public List<ConsentResponse> listAll() {
        return consents.findAll().stream().map(ConsentResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ConsentResponse getById(UUID id) {
        return consents
                .findById(id)
                .map(ConsentResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consent not found"));
    }

    @Transactional
    public ConsentResponse create(CreateConsentRequest request) {
        var scopes = String.join(",", request.scopes());
        var entity = new Consent(request.tppId().trim(), scopes, request.customerId().trim(), request.validUntil());
        return ConsentResponse.from(consents.save(entity));
    }

    @Transactional
    public ConsentResponse revoke(UUID id) {
        Consent c =
                consents.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consent not found"));
        c.setStatus(ConsentStatus.REVOKED);
        return ConsentResponse.from(consents.save(c));
    }
}
