package com.bank.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bank.transaction.client.AccountValidationClient;
import com.bank.transaction.client.FraudEvaluationClient;
import com.bank.transaction.domain.Transaction;
import com.bank.transaction.domain.TransactionEvent;
import com.bank.transaction.domain.TransactionMovementType;
import com.bank.transaction.domain.TransactionStatus;
import com.bank.transaction.repository.TransactionEventRepository;
import com.bank.transaction.repository.TransactionRepository;
import com.bank.transaction.service.TransactionService;
import com.bank.transaction.web.dto.CreateTransactionRequest;
import org.springframework.beans.factory.ObjectProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactions;

    @Mock
    private TransactionEventRepository events;

    @Mock
    private AccountValidationClient accountValidationClient;

    @Mock
    private ObjectProvider<FraudEvaluationClient> fraudClient;

    @Mock
    private FraudEvaluationClient fraudEvaluationClient;

    private TransactionService service;

    @BeforeEach
    void setUp() {
        service = new TransactionService(transactions, events, accountValidationClient, new ObjectMapper(), fraudClient);
        lenient()
                .doAnswer(
                        inv -> {
                            Consumer<FraudEvaluationClient> c = inv.getArgument(0);
                            c.accept(fraudEvaluationClient);
                            return null;
                        })
                .when(fraudClient)
                .ifAvailable(any());
        lenient()
                .when(fraudEvaluationClient.evaluateVerdict(any(), any(), any(), any()))
                .thenReturn("ALLOW");
    }

    @Test
    void createTransactionStoresPendingAndOutbox() {
        UUID acc = UUID.randomUUID();
        when(transactions.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        var req = new CreateTransactionRequest(
                acc, new BigDecimal("10"), TransactionMovementType.DEBIT, "GBP", "d", "ref");
        var res = service.createTransaction(req);

        assertThat(res.status()).isEqualTo(TransactionStatus.PENDING);
        verify(accountValidationClient).ensureAccountExists(acc);
        verify(events).save(any(TransactionEvent.class));
    }

    @Test
    void completeTransactionMarksCompletedAndOutboxes() {
        UUID id = UUID.randomUUID();
        UUID acc = UUID.randomUUID();
        var tx = new Transaction(
                acc, new BigDecimal("5"), "EUR", TransactionMovementType.CREDIT, null, "x", TransactionStatus.PENDING);
        setId(tx, id);
        when(transactions.findById(id)).thenReturn(Optional.of(tx));
        when(transactions.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        var res = service.completeTransaction(id, "device-1");

        assertThat(res.status()).isEqualTo(TransactionStatus.COMPLETED);
        ArgumentCaptor<TransactionEvent> cap = ArgumentCaptor.forClass(TransactionEvent.class);
        verify(events).save(cap.capture());
        assertThat(cap.getValue().getEventType()).isEqualTo(TransactionService.EVT_COMPLETED);
    }

    @Test
    void completeTransactionHeldUnderReviewWhenFraudReturnsReview() {
        UUID id = UUID.randomUUID();
        UUID acc = UUID.randomUUID();
        var tx = new Transaction(
                acc,
                new BigDecimal("6000"),
                "EUR",
                TransactionMovementType.DEBIT,
                null,
                "high",
                TransactionStatus.PENDING);
        setId(tx, id);
        when(transactions.findById(id)).thenReturn(Optional.of(tx));
        when(transactions.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));
        when(fraudEvaluationClient.evaluateVerdict(any(), any(), any(), any())).thenReturn("REVIEW");

        var res = service.completeTransaction(id, "device-1");

        assertThat(res.status()).isEqualTo(TransactionStatus.UNDER_REVIEW);
        ArgumentCaptor<TransactionEvent> cap = ArgumentCaptor.forClass(TransactionEvent.class);
        verify(events).save(cap.capture());
        assertThat(cap.getValue().getEventType()).isEqualTo(TransactionService.EVT_FRAUD_REVIEW);
    }

    private static void setId(Transaction t, UUID id) {
        try {
            var f = Transaction.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(t, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
