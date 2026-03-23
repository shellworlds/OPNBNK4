package com.bank.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class OpenbankingRateLimitConfig {

    @Bean
    public KeyResolver openbankingRateLimitKeyResolver() {
        return exchange -> {
            String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (auth != null && !auth.isBlank()) {
                return Mono.just("auth:" + Integer.toHexString(auth.hashCode()));
            }
            String xff = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
            if (xff != null && !xff.isBlank()) {
                return Mono.just("ip:" + xff.split(",")[0].trim());
            }
            var remote = exchange.getRequest().getRemoteAddress();
            if (remote != null && remote.getAddress() != null) {
                return Mono.just("ip:" + remote.getAddress().getHostAddress());
            }
            return Mono.just("anon");
        };
    }
}
