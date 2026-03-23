package com.bank.account.web;

import com.bank.account.service.AccountService;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * GDPR-style stubs: export portable view of customer-linked accounts; request account erasure (async
 * anonymization in a real system). Protect with strong auth in production.
 */
@RestController
@RequestMapping("/api/user")
public class UserDataController {

    private final AccountService accountService;

    public UserDataController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/export")
    public Map<String, Object> export(@RequestParam String customerId) {
        var accounts = accountService.listForCustomer(customerId);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("customerId", customerId);
        body.put("exportedAt", Instant.now().toString());
        body.put("accounts", accounts);
        body.put("formatVersion", 1);
        return body;
    }

    @PostMapping("/delete-request")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, Object> deleteRequest(@RequestParam String customerId) {
        return Map.of(
                "customerId",
                customerId,
                "status",
                "ACCEPTED",
                "message",
                "Stub: schedule soft-delete and PII anonymization (implement in production workflow)");
    }
}
