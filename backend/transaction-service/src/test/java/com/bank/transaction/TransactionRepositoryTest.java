package com.bank.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.transaction.domain.Transaction;
import com.bank.transaction.domain.TransactionMovementType;
import com.bank.transaction.domain.TransactionStatus;
import com.bank.transaction.repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private TransactionRepository repository;

    @Test
    void findByAccountId() {
        UUID acc = UUID.randomUUID();
        em.persist(new Transaction(
                acc, new BigDecimal("10"), "GBP", TransactionMovementType.DEBIT, null, "r1", TransactionStatus.PENDING));
        em.persist(new Transaction(
                acc, new BigDecimal("5"), "GBP", TransactionMovementType.CREDIT, null, "r2", TransactionStatus.COMPLETED));
        em.flush();
        assertThat(repository.findByAccountIdOrderByCreatedAtDesc(acc)).hasSize(2);
    }
}
