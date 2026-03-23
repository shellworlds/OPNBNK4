package com.bank.openbanking.web;

import com.bank.openbanking.service.OpenbankingDownstreamService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/openbanking")
public class OpenbankingAisController {

    private final OpenbankingDownstreamService downstream;

    public OpenbankingAisController(OpenbankingDownstreamService downstream) {
        this.downstream = downstream;
    }

    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listAccounts(
            HttpServletRequest request,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        String customerId = (String) request.getAttribute("ob.customerId");
        return downstream.listAccountsForCustomer(customerId, authorization);
    }

    @GetMapping(value = "/accounts/{accountId}/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public String listTransactionsForAccount(
            HttpServletRequest request,
            @PathVariable UUID accountId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        String customerId = (String) request.getAttribute("ob.customerId");
        var acc = downstream.getAccount(accountId, authorization);
        if (!customerId.equals(acc.get("customerId").asText())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account not covered by consent subject");
        }
        return downstream.getTransactionsForAccount(accountId, authorization);
    }
}
