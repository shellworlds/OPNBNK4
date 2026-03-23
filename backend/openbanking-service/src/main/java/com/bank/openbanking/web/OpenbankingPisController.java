package com.bank.openbanking.web;

import com.bank.openbanking.service.OpenbankingDownstreamService;
import com.bank.openbanking.web.dto.CreatePaymentRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openbanking")
public class OpenbankingPisController {

    private final OpenbankingDownstreamService downstream;

    public OpenbankingPisController(OpenbankingDownstreamService downstream) {
        this.downstream = downstream;
    }

    @PostMapping("/payments")
    public Map<String, Object> initiatePayment(
            HttpServletRequest request,
            @Valid @RequestBody CreatePaymentRequest body,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        request.getAttribute("ob.customerId"); // validated by filter
        UUID txId = downstream.initiatePayment(body, authorization);
        return Map.of("transactionId", txId, "status", "COMPLETED");
    }
}
