package com.bank.fraud.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class RuleEngine {

    private static final BigDecimal EUR_REVIEW_THRESHOLD = new BigDecimal("5000");
    private static final BigDecimal EUR_BLOCK_THRESHOLD = new BigDecimal("50000");

    private final Map<String, Set<String>> devicesByAccount = new ConcurrentHashMap<>();

    public enum Verdict {
        ALLOW,
        REVIEW,
        BLOCK
    }

    public Verdict evaluateSync(
            String accountId, BigDecimal amount, String currency, String deviceFingerprint) {
        BigDecimal eurEquivalent = toEur(amount, currency);
        if (eurEquivalent.compareTo(EUR_BLOCK_THRESHOLD) > 0) {
            return Verdict.BLOCK;
        }
        if (eurEquivalent.compareTo(EUR_REVIEW_THRESHOLD) > 0) {
            return Verdict.REVIEW;
        }
        if (deviceFingerprint == null || deviceFingerprint.isBlank()) {
            return Verdict.REVIEW;
        }
        Set<String> seen = devicesByAccount.computeIfAbsent(accountId, k -> ConcurrentHashMap.newKeySet());
        if (!seen.contains(deviceFingerprint)) {
            seen.add(deviceFingerprint);
            if (seen.size() > 1) {
                return Verdict.REVIEW;
            }
        }
        return Verdict.ALLOW;
    }

    private static BigDecimal toEur(BigDecimal amount, String currency) {
        if (currency == null) {
            return amount;
        }
        return switch (currency.toUpperCase()) {
            case "EUR" -> amount;
            case "GBP" -> amount.multiply(new BigDecimal("1.17"));
            case "USD" -> amount.multiply(new BigDecimal("0.92"));
            default -> amount;
        };
    }
}
