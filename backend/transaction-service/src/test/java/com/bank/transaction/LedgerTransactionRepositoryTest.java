package com.bank.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.transaction.domain.LedgerTransaction;
import com.bank.transaction.repository.LedgerTransactionRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class LedgerTransactionRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private LedgerTransactionRepository repository;

    @Test
    void findByAccountIdOrder() {
        UUID acc = UUID.randomUUID();
        em.persist(new LedgerTransaction(acc, new BigDecimal("-10"), "GBP", "coffee"));
        em.persist(new LedgerTransaction(acc, new BigDecimal("100"), "GBP", "salary"));
        em.flush();
        assertThat(repository.findByAccountIdOrderByBookedAtDesc(acc)).hasSize(2);
    }
}
