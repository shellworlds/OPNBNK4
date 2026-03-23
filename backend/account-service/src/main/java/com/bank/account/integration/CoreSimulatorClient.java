package com.bank.account.integration;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.math.BigDecimal;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@ConditionalOnProperty(name = "core.integration.enabled", havingValue = "true")
public class CoreSimulatorClient {

    private final WebClient webClient;

    public CoreSimulatorClient(
            WebClient.Builder webClientBuilder, @Value("${core.integration.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @CircuitBreaker(name = "coreSimulator", fallbackMethod = "ledgerFallback")
    public CoreLedgerDto fetchLedger(String accountId) {
        return webClient
                .get()
                .uri("/api/core/account/{id}/ledger", accountId)
                .retrieve()
                .bodyToMono(CoreLedgerDto.class)
                .timeout(Duration.ofSeconds(2))
                .block();
    }

    @SuppressWarnings("unused")
    private CoreLedgerDto ledgerFallback(String accountId, Throwable t) {
        return null;
    }

    @CircuitBreaker(name = "coreSimulator", fallbackMethod = "customerFallback")
    public CoreCustomerDto fetchCustomer(String customerId) {
        return webClient
                .get()
                .uri("/api/core/customer/{id}", customerId)
                .retrieve()
                .bodyToMono(CoreCustomerDto.class)
                .timeout(Duration.ofSeconds(2))
                .block();
    }

    @SuppressWarnings("unused")
    private CoreCustomerDto customerFallback(String customerId, Throwable t) {
        return null;
    }

    public record CoreLedgerDto(BigDecimal bookBalance, String currency, String systemId) {}

    public record CoreCustomerDto(String id, String displayName, String status) {}
}
