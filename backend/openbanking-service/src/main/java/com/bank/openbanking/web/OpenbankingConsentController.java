package com.bank.openbanking.web;

import com.bank.openbanking.service.ConsentService;
import com.bank.openbanking.web.dto.ConsentResponse;
import com.bank.openbanking.web.dto.CreateConsentRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openbanking/consents")
public class OpenbankingConsentController {

    private final ConsentService consentService;

    public OpenbankingConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsentResponse create(@Valid @RequestBody CreateConsentRequest request) {
        return consentService.create(request);
    }

    @GetMapping("/{id}")
    public ConsentResponse get(@PathVariable UUID id) {
        return consentService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revoke(@PathVariable UUID id) {
        consentService.revoke(id);
    }
}
