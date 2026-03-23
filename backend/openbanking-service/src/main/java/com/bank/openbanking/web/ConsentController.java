package com.bank.openbanking.web;

import com.bank.openbanking.service.ConsentService;
import com.bank.openbanking.web.dto.ConsentResponse;
import com.bank.openbanking.web.dto.CreateConsentRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/openbanking/consents")
public class ConsentController {

    private final ConsentService consentService;

    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

    @GetMapping
    public List<ConsentResponse> listConsents() {
        return consentService.listAll();
    }

    @GetMapping("/{id}")
    public ConsentResponse getConsent(@PathVariable UUID id) {
        return consentService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsentResponse create(@Valid @RequestBody CreateConsentRequest request) {
        return consentService.create(request);
    }

    @PostMapping("/{id}/revoke")
    public ConsentResponse revoke(@PathVariable UUID id) {
        return consentService.revoke(id);
    }
}
