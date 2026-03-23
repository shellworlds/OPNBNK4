package com.bank.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.account.domain.Account;
import com.bank.account.domain.AccountStatus;
import com.bank.account.domain.AccountType;
import com.bank.account.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AccountRepository repository;

    @Test
    void saveAndFindByAccountNumber() {
        var acc = new Account("c1", "GB82WEST12345698765432", AccountType.CHECKING, "GBP", new BigDecimal("100.00"), AccountStatus.ACTIVE);
        em.persistFlushFind(acc);
        assertThat(repository.findByAccountNumber("GB82WEST12345698765432")).isPresent();
    }

    @Test
    void findByCustomerId() {
        em.persist(new Account("c1", "GB11AAAA11111111111111", AccountType.CHECKING, "GBP", BigDecimal.ONE, AccountStatus.ACTIVE));
        em.persist(new Account("c1", "GB22BBBB22222222222222", AccountType.SAVINGS, "GBP", BigDecimal.TEN, AccountStatus.ACTIVE));
        em.flush();
        assertThat(repository.findByCustomerId("c1")).hasSize(2);
    }

    @Test
    void findById() {
        var acc = new Account("x", "GB33CCCC33333333333333", AccountType.CHECKING, "EUR", new BigDecimal("50"), AccountStatus.ACTIVE);
        UUID id = em.persistFlushFind(acc).getId();
        assertThat(repository.findById(id)).isPresent();
    }
}
