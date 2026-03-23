package com.bank.openbanking.web;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/openbanking/consents")
public class ConsentController {

    @GetMapping
    public List<Map<String, Object>> listConsents() {
        return List.of(
                Map.of(
                        "consentId",
                        "cns-7a2f",
                        "tppId",
                        "tpp-demo",
                        "scopes",
                        List.of("accounts:read", "transactions:read"),
                        "status",
                        "ACTIVE"));
    }
}
