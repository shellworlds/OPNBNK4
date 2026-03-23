package com.bank.transaction.web;

import com.bank.transaction.service.LedgerTransactionService;
import com.bank.transaction.web.dto.CreateTransactionRequest;
import com.bank.transaction.web.dto.TransactionResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final LedgerTransactionService transactionService;

    public TransactionController(LedgerTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionResponse> listTransactions(@RequestParam(required = false) UUID accountId) {
        if (accountId != null) {
            return transactionService.listForAccount(accountId);
        }
        return transactionService.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(@Valid @RequestBody CreateTransactionRequest request) {
        return transactionService.create(request);
    }
}
