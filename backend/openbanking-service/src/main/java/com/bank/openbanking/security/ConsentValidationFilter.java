package com.bank.openbanking.security;

import com.bank.openbanking.service.ConsentService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ConsentValidationFilter extends OncePerRequestFilter {

    private final ConsentService consentService;

    public ConsentValidationFilter(ConsentService consentService) {
        this.consentService = consentService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return !uri.startsWith("/openbanking/") || uri.startsWith("/openbanking/consents");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tpp = header(request, "X-Demo-Tpp-Id");
        String customer = header(request, "X-Demo-Customer-Id");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwt) {
            if (tpp == null) {
                tpp = jwt.getToken().getClaimAsString("azp");
            }
            if (customer == null) {
                customer = jwt.getToken().getSubject();
            }
        }
        if (tpp == null || customer == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "TPP and customer required (JWT azp/sub or X-Demo-Tpp-Id / X-Demo-Customer-Id)");
            return;
        }
        String scope = requiredScope(request.getMethod(), request.getRequestURI());
        try {
            consentService.assertActiveConsent(tpp, customer, scope);
        } catch (org.springframework.web.server.ResponseStatusException e) {
            response.sendError(e.getStatusCode().value(), e.getReason());
            return;
        }
        request.setAttribute("ob.customerId", customer);
        request.setAttribute("ob.tppId", tpp);
        filterChain.doFilter(request, response);
    }

    private static String header(HttpServletRequest r, String name) {
        String v = r.getHeader(name);
        return v == null || v.isBlank() ? null : v.trim();
    }

    static String requiredScope(String method, String uri) {
        if (uri.contains("/openbanking/payments")) {
            return "payments:write";
        }
        if (uri.contains("/transactions")) {
            return "transactions:read";
        }
        return "accounts:read";
    }
}
