package com.bank.gateway.web;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GatewayFallbackController {

    @RequestMapping("/fallback/accounts")
    public Mono<ResponseEntity<Map<String, Object>>> accountsFallback() {
        return Mono.just(
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(Map.of("message", "Account service temporarily unavailable", "degraded", true)));
    }

    @RequestMapping("/fallback/transactions")
    public Mono<ResponseEntity<Map<String, Object>>> transactionsFallback() {
        return Mono.just(
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(Map.of("message", "Transaction service temporarily unavailable", "degraded", true)));
    }

    @RequestMapping("/fallback/openbanking")
    public Mono<ResponseEntity<Map<String, Object>>> openbankingFallback() {
        return Mono.just(
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(Map.of("message", "Open banking service temporarily unavailable", "degraded", true)));
    }
}
