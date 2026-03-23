package com.bank.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.account.domain.BankAccount;
import com.bank.account.repository.BankAccountRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class BankAccountRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BankAccountRepository repository;

    @Test
    void saveAndFindByIban() {
        var acc = new BankAccount("GB82WEST12345698765432", "GBP", new BigDecimal("100.00"), "cust-1");
        em.persistFlushFind(acc);
        assertThat(repository.findByIban("GB82WEST12345698765432")).isPresent();
    }

    @Test
    void findByCustomerIdOrdersByCreatedAt() {
        var a1 = new BankAccount("GB11AAAA11111111111111", "GBP", BigDecimal.ONE, "c1");
        var a2 = new BankAccount("GB22BBBB22222222222222", "GBP", BigDecimal.TEN, "c1");
        em.persist(a1);
        em.persist(a2);
        em.flush();
        assertThat(repository.findByCustomerIdOrderByCreatedAtDesc("c1")).hasSize(2);
    }

    @Test
    void findById() {
        var acc = new BankAccount("GB33CCCC33333333333333", "EUR", new BigDecimal("50"), "x");
        UUID id = em.persistFlushFind(acc).getId();
        assertThat(repository.findById(id)).isPresent();
    }
}
