package com.bank.transaction.web;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @GetMapping
    public List<Map<String, Object>> listTransactions() {
        return List.of(
                Map.of(
                        "id",
                        "txn-1001",
                        "accountId",
                        "acc-001",
                        "amount",
                        -45.99,
                        "currency",
                        "GBP",
                        "bookedAt",
                        Instant.parse("2026-03-22T10:15:30Z")),
                Map.of(
                        "id",
                        "txn-1002",
                        "accountId",
                        "acc-001",
                        "amount",
                        1200.00,
                        "currency",
                        "GBP",
                        "bookedAt",
                        Instant.parse("2026-03-21T09:00:00Z")));
    }
}
