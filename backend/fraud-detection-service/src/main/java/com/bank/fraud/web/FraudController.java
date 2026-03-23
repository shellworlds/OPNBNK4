package com.bank.fraud.web;

import com.bank.fraud.service.RuleEngine;
import com.bank.fraud.service.RuleEngine.Verdict;
import com.bank.fraud.web.dto.FraudEvaluateRequest;
import com.bank.fraud.web.dto.FraudEvaluateResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fraud")
public class FraudController {

    private final RuleEngine ruleEngine;

    public FraudController(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    @PostMapping("/evaluate")
    public FraudEvaluateResponse evaluate(@RequestBody FraudEvaluateRequest req) {
        Verdict v = ruleEngine.evaluateSync(
                req.accountId(), req.amount(), req.currency(), req.deviceFingerprint());
        String reason =
                switch (v) {
                    case ALLOW -> "rules_passed";
                    case REVIEW -> "high_amount_or_new_device";
                    case BLOCK -> "blocked";
                };
        return new FraudEvaluateResponse(v, reason);
    }
}
