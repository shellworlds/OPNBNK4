package com.bank.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.bank.transaction.domain.LedgerTransaction;
import com.bank.transaction.repository.LedgerTransactionRepository;
import com.bank.transaction.service.LedgerTransactionService;
import com.bank.transaction.web.dto.CreateTransactionRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LedgerTransactionServiceTest {

    @Mock
    private LedgerTransactionRepository repo;

    @InjectMocks
    private LedgerTransactionService service;

    @Test
    void createUppercasesCurrency() {
        UUID acc = UUID.randomUUID();
        when(repo.save(any(LedgerTransaction.class))).thenAnswer(inv -> inv.getArgument(0));
        var res = service.create(new CreateTransactionRequest(acc, BigDecimal.ONE, "gbp", "r"));
        assertThat(res.currency()).isEqualTo("GBP");
    }

    @Test
    void listForAccountDelegates() {
        UUID acc = UUID.randomUUID();
        when(repo.findByAccountIdOrderByBookedAtDesc(acc))
                .thenReturn(List.of(new LedgerTransaction(acc, BigDecimal.TEN, "GBP", null)));
        assertThat(service.listForAccount(acc)).hasSize(1);
    }
}
