package com.bank.transaction.web;

import com.bank.transaction.service.TransactionService;
import com.bank.transaction.web.dto.CreateTransactionRequest;
import com.bank.transaction.web.dto.TransactionResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(@Valid @RequestBody CreateTransactionRequest request) {
        return transactionService.createTransaction(request);
    }

    @PostMapping("/{id}/complete")
    public TransactionResponse complete(
            @PathVariable UUID id,
            @RequestHeader(value = "X-Device-Id", required = false) String deviceId) {
        return transactionService.completeTransaction(id, deviceId);
    }

    @GetMapping("/account/{accountId}")
    public List<TransactionResponse> listForAccount(@PathVariable UUID accountId) {
        return transactionService.listForAccount(accountId);
    }
}
