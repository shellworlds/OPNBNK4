package com.bank.transaction.client;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@ConditionalOnProperty(name = "fraud.evaluation.enabled", havingValue = "true")
public class FraudEvaluationClient {

    private final WebClient webClient;

    public FraudEvaluationClient(
            WebClient.Builder webClientBuilder,
            @org.springframework.beans.factory.annotation.Value("${fraud.evaluation.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public String evaluateVerdict(UUID accountId, BigDecimal amount, String currency, String deviceFingerprint) {
        FraudApiResponse res =
                webClient
                        .post()
                        .uri("/api/fraud/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(
                                Map.of(
                                        "accountId",
                                        accountId.toString(),
                                        "amount",
                                        amount,
                                        "currency",
                                        currency,
                                        "deviceFingerprint",
                                        deviceFingerprint == null ? "" : deviceFingerprint))
                        .retrieve()
                        .bodyToMono(FraudApiResponse.class)
                        .block(Duration.ofSeconds(3));
        return res != null ? res.verdict() : "ALLOW";
    }
}
