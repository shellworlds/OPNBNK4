package com.bank.fraud.web.dto;

import com.bank.fraud.service.RuleEngine.Verdict;

public record FraudEvaluateResponse(Verdict verdict, String reason) {}
