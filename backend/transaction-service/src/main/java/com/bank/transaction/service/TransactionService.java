package com.bank.transaction.service;

import com.bank.events.TransactionCompletedEvent;
import com.bank.events.TransactionCreatedEvent;
import com.bank.transaction.client.AccountValidationClient;
import com.bank.transaction.client.FraudEvaluationClient;
import com.bank.transaction.domain.Transaction;
import com.bank.transaction.domain.TransactionEvent;
import com.bank.transaction.domain.TransactionMovementType;
import com.bank.transaction.domain.TransactionStatus;
import com.bank.transaction.repository.TransactionEventRepository;
import com.bank.transaction.repository.TransactionRepository;
import com.bank.transaction.web.dto.CreateTransactionRequest;
import com.bank.transaction.web.dto.TransactionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransactionService {

    public static final String EVT_CREATED = "TRANSACTION_CREATED";
    public static final String EVT_COMPLETED = "TRANSACTION_COMPLETED";

    private final TransactionRepository transactionRepository;
    private final TransactionEventRepository eventRepository;
    private final AccountValidationClient accountValidationClient;
    private final ObjectMapper objectMapper;
    private final ObjectProvider<FraudEvaluationClient> fraudEvaluationClient;

    public TransactionService(
            TransactionRepository transactionRepository,
            TransactionEventRepository eventRepository,
            AccountValidationClient accountValidationClient,
            ObjectMapper objectMapper,
            ObjectProvider<FraudEvaluationClient> fraudEvaluationClient) {
        this.transactionRepository = transactionRepository;
        this.eventRepository = eventRepository;
        this.accountValidationClient = accountValidationClient;
        this.objectMapper = objectMapper;
        this.fraudEvaluationClient = fraudEvaluationClient;
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> listForAccount(UUID accountId) {
        return transactionRepository.findByAccountIdOrderByCreatedAtDesc(accountId).stream()
                .map(TransactionResponse::from)
                .toList();
    }

    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        accountValidationClient.ensureAccountExists(request.accountId());
        var tx = new Transaction(
                request.accountId(),
                request.amount(),
                request.currency().trim().toUpperCase(),
                request.type(),
                request.description(),
                request.reference(),
                TransactionStatus.PENDING);
        tx = transactionRepository.save(tx);
        var eventPayload = new TransactionCreatedEvent(
                tx.getId(),
                tx.getAccountId(),
                tx.getAmount(),
                tx.getType().name(),
                tx.getCurrency(),
                tx.getReference());
        eventRepository.save(new TransactionEvent(tx.getId(), EVT_CREATED, writeJson(eventPayload)));
        return TransactionResponse.from(tx);
    }

    @Transactional
    public TransactionResponse completeTransaction(UUID id, String deviceFingerprint) {
        Transaction tx = transactionRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
        if (tx.getStatus() != TransactionStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Transaction is not pending");
        }
        fraudEvaluationClient.ifAvailable(
                client -> {
                    String verdict =
                            client.evaluateVerdict(
                                    tx.getAccountId(),
                                    tx.getAmount(),
                                    tx.getCurrency(),
                                    deviceFingerprint);
                    if ("BLOCK".equalsIgnoreCase(verdict)) {
                        throw new ResponseStatusException(
                                HttpStatus.FORBIDDEN, "Transaction blocked by fraud policy");
                    }
                });
        tx.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(tx);
        var completed = new TransactionCompletedEvent(
                tx.getId(),
                tx.getAccountId(),
                tx.getAmount(),
                tx.getType().name(),
                tx.getCurrency(),
                tx.getReference());
        eventRepository.save(new TransactionEvent(tx.getId(), EVT_COMPLETED, writeJson(completed)));
        return TransactionResponse.from(tx);
    }

    private String writeJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
