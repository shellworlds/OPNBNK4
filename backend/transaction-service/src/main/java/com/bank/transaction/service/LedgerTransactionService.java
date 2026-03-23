package com.bank.transaction.service;

import com.bank.transaction.domain.LedgerTransaction;
import com.bank.transaction.repository.LedgerTransactionRepository;
import com.bank.transaction.web.dto.CreateTransactionRequest;
import com.bank.transaction.web.dto.TransactionResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LedgerTransactionService {

    private final LedgerTransactionRepository transactions;

    public LedgerTransactionService(LedgerTransactionRepository transactions) {
        this.transactions = transactions;
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> listAll() {
        return transactions.findAll().stream().map(TransactionResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> listForAccount(UUID accountId) {
        return transactions.findByAccountIdOrderByBookedAtDesc(accountId).stream()
                .map(TransactionResponse::from)
                .toList();
    }

    @Transactional
    public TransactionResponse create(CreateTransactionRequest request) {
        var entity = new LedgerTransaction(
                request.accountId(),
                request.amount(),
                request.currency().trim().toUpperCase(),
                request.reference());
        return TransactionResponse.from(transactions.save(entity));
    }
}
