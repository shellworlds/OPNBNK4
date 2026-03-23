package com.bank.fraud.web.dto;

import java.math.BigDecimal;

public record FraudEvaluateRequest(
        String accountId, BigDecimal amount, String currency, String deviceFingerprint) {}
