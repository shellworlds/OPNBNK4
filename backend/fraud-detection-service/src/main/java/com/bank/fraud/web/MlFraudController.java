package com.bank.fraud.web;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Placeholder for a hosted scikit-learn / ONNX model. Replace with a real inference service in production.
 */
@RestController
@RequestMapping("/api/fraud/ml")
public class MlFraudController {

    @PostMapping("/predict")
    public Map<String, Object> predict(@RequestBody Map<String, Object> features) {
        Object amt = features.get("amount");
        BigDecimal amount =
                amt instanceof Number n ? BigDecimal.valueOf(n.doubleValue()) : BigDecimal.ZERO;
        double score = Math.min(1.0, amount.doubleValue() / 100_000.0);
        String label = score > 0.05 ? "suspicious" : "normal";
        return Map.of("model", "dummy-logistic-v0", "fraudScore", score, "label", label);
    }
}
