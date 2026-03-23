package com.bank.openbanking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.bank.openbanking.domain.Consent;
import com.bank.openbanking.domain.ConsentStatus;
import com.bank.openbanking.repository.ConsentRepository;
import com.bank.openbanking.service.ConsentService;
import com.bank.openbanking.web.dto.CreateConsentRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class ConsentServiceTest {

    @Mock
    private ConsentRepository repo;

    @InjectMocks
    private ConsentService service;

    @Test
    void createJoinsScopes() {
        when(repo.save(any(Consent.class))).thenAnswer(inv -> inv.getArgument(0));
        var res = service.create(new CreateConsentRequest("tpp", List.of("a", "b"), "c", null));
        assertThat(res.scopes()).containsExactly("a", "b");
    }

    @Test
    void revoke() {
        UUID id = UUID.randomUUID();
        var c = new Consent("t", "s", "x", null);
        when(repo.findById(id)).thenReturn(Optional.of(c));
        when(repo.save(any(Consent.class))).thenAnswer(inv -> inv.getArgument(0));
        assertThat(service.revoke(id).status()).isEqualTo(ConsentStatus.REVOKED);
    }

    @Test
    void getMissingThrows() {
        when(repo.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(UUID.randomUUID())).isInstanceOf(ResponseStatusException.class);
    }
}
