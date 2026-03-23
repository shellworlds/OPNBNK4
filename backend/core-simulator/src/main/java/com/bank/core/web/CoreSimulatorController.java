package com.bank.core.web;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/core")
public class CoreSimulatorController {

    private final Map<String, CustomerRecord> customers = new ConcurrentHashMap<>();
    private final Map<String, LedgerRecord> ledgers = new ConcurrentHashMap<>();

    public CoreSimulatorController() {
        customers.put("open-sample-uk-001", new CustomerRecord("open-sample-uk-001", "Demo UK Customer", "ACTIVE"));
        customers.put("open-sample-de-001", new CustomerRecord("open-sample-de-001", "Demo DE Customer", "ACTIVE"));
        ledgers.put(
                "placeholder",
                new LedgerRecord("placeholder", new BigDecimal("100000.00"), "EUR", "BOOK"));
    }

    @GetMapping("/customer/{id}")
    public CustomerRecord customer(@PathVariable String id) {
        return customers.getOrDefault(id, new CustomerRecord(id, "Simulated customer " + id, "ACTIVE"));
    }

    /**
     * Legacy core ledger view (simulated). Real integration would key on core account id; here we accept platform
     * account UUID as opaque key and return a deterministic stub balance unless seeded.
     */
    @GetMapping("/account/{accountId}/ledger")
    public LedgerRecord ledger(@PathVariable String accountId) {
        return ledgers.getOrDefault(
                accountId,
                new LedgerRecord(accountId, new BigDecimal("25000.00"), "EUR", "SIMULATED"));
    }

    public record CustomerRecord(String id, String displayName, String status) {}

    public record LedgerRecord(String accountId, BigDecimal bookBalance, String currency, String systemId) {}
}
