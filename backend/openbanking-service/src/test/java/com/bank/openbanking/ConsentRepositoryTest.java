package com.bank.openbanking;

import static org.assertj.core.api.Assertions.assertThat;

import com.bank.openbanking.domain.Consent;
import com.bank.openbanking.domain.ConsentStatus;
import com.bank.openbanking.repository.ConsentRepository;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class ConsentRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ConsentRepository repository;

    @Test
    void saveAndFind() {
        var c = new Consent("cust-1", "tpp-aisp-demo", "[\"accounts:read\"]", Instant.now(), null);
        em.persistFlushFind(c);
        assertThat(repository.findAll()).hasSize(1);
        assertThat(c.getStatus()).isEqualTo(ConsentStatus.ACTIVE);
    }
}
