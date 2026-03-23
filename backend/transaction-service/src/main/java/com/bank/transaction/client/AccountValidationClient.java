package com.bank.transaction.client;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AccountValidationClient {

    private final WebClient webClient;
    private final String baseUrl;

    public AccountValidationClient(
            WebClient.Builder webClientBuilder, @Value("${account.service.base-url:}") String baseUrl) {
        this.webClient = webClientBuilder.build();
        this.baseUrl = baseUrl == null ? "" : baseUrl.trim();
    }

    public void ensureAccountExists(UUID accountId) {
        if (baseUrl.isEmpty()) {
            return;
        }
        try {
            webClient
                    .get()
                    .uri(baseUrl + "/api/accounts/{id}", accountId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account not found: " + accountId);
        }
    }
}
