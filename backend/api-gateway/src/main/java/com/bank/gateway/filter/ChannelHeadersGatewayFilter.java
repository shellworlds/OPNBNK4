package com.bank.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(org.springframework.core.Ordered.HIGHEST_PRECEDENCE + 20)
public class ChannelHeadersGatewayFilter implements WebFilter {

    @Value("${gateway.channel-validation.enabled:false}")
    private boolean enabled;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (!enabled) {
            return chain.filter(exchange);
        }
        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/actuator") || path.startsWith("/fallback")) {
            return chain.filter(exchange);
        }
        var h = exchange.getRequest().getHeaders();
        if (h.getFirst("X-Client-Id") == null || h.getFirst("X-Client-Channel") == null) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
}
