package com.bank.openbanking.service;

import com.bank.openbanking.web.dto.CreatePaymentRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenbankingDownstreamService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String accountBaseUrl;
    private final String transactionBaseUrl;

    public OpenbankingDownstreamService(
            WebClient.Builder webClientBuilder,
            ObjectMapper objectMapper,
            @Value("${account.service.base-url:}") String accountBaseUrl,
            @Value("${transaction.service.base-url:}") String transactionBaseUrl) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
        this.accountBaseUrl = trim(accountBaseUrl);
        this.transactionBaseUrl = trim(transactionBaseUrl);
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    public String listAccountsForCustomer(String customerId, String authorizationHeader) {
        require(accountBaseUrl);
        return webClient
                .get()
                .uri(accountBaseUrl + "/api/accounts/customer/{cid}", customerId)
                .headers(h -> forwardAuth(h, authorizationHeader))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getTransactionsForAccount(UUID accountId, String authorizationHeader) {
        require(transactionBaseUrl);
        return webClient
                .get()
                .uri(transactionBaseUrl + "/api/transactions/account/{aid}", accountId)
                .headers(h -> forwardAuth(h, authorizationHeader))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public JsonNode getAccount(UUID accountId, String authorizationHeader) {
        require(accountBaseUrl);
        String body = webClient
                .get()
                .uri(accountBaseUrl + "/api/accounts/{id}", accountId)
                .headers(h -> forwardAuth(h, authorizationHeader))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            return objectMapper.readTree(body);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public UUID initiatePayment(CreatePaymentRequest request, String authorizationHeader) {
        require(transactionBaseUrl);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("accountId", request.accountId());
        body.put("amount", request.amount());
        body.put("type", request.type());
        body.put("currency", request.currency());
        body.put("reference", request.reference() != null ? request.reference() : "PIS");
        body.put("description", "Open banking payment");

        JsonNode created = webClient
                .post()
                .uri(transactionBaseUrl + "/api/transactions")
                .headers(h -> {
                    forwardAuth(h, authorizationHeader);
                    h.set(HttpHeaders.CONTENT_TYPE, "application/json");
                })
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        UUID id = UUID.fromString(created.get("id").asText());
        webClient
                .post()
                .uri(transactionBaseUrl + "/api/transactions/{id}/complete", id)
                .headers(h -> forwardAuth(h, authorizationHeader))
                .retrieve()
                .toBodilessEntity()
                .block();
        return id;
    }

    private static void forwardAuth(org.springframework.http.HttpHeaders h, String authorizationHeader) {
        if (authorizationHeader != null && !authorizationHeader.isBlank()) {
            h.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
        }
    }

    private static void require(String base) {
        if (base.isEmpty()) {
            throw new IllegalStateException("Downstream base URL not configured");
        }
    }
}
