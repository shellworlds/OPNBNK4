package com.bank.openbanking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bank.openbanking.domain.Consent;
import com.bank.openbanking.domain.ConsentStatus;
import com.bank.openbanking.domain.Tpp;
import com.bank.openbanking.repository.ConsentRepository;
import com.bank.openbanking.repository.TppRepository;
import com.bank.openbanking.service.ConsentService;
import com.bank.openbanking.web.dto.CreateConsentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class ConsentServiceTest {

    @Mock
    private ConsentRepository repo;

    @Mock
    private TppRepository tpps;

    private ConsentService service;

    @BeforeEach
    void setUp() {
        service = new ConsentService(repo, tpps, new ObjectMapper());
    }

    @Test
    void createStoresPermissionsJson() {
        when(tpps.findByExternalId("tpp")).thenReturn(Optional.of(new Tpp(UUID.randomUUID(), "tpp", "Demo", null)));
        when(repo.save(any(Consent.class))).thenAnswer(inv -> inv.getArgument(0));
        var res = service.create(new CreateConsentRequest("c", "tpp", List.of("a", "b"), null, null, null));
        assertThat(res.permissions()).containsExactly("a", "b");
    }

    @Test
    void revoke() {
        UUID id = UUID.randomUUID();
        var c = new Consent("c", "tpp", "[\"x\"]", null, null);
        setId(c, id);
        when(repo.findById(id)).thenReturn(Optional.of(c));
        when(repo.save(any(Consent.class))).thenAnswer(inv -> inv.getArgument(0));
        service.revoke(id);
        ArgumentCaptor<Consent> cap = ArgumentCaptor.forClass(Consent.class);
        verify(repo).save(cap.capture());
        assertThat(cap.getValue().getStatus()).isEqualTo(ConsentStatus.REVOKED);
    }

    @Test
    void getMissingThrows() {
        when(repo.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(UUID.randomUUID())).isInstanceOf(ResponseStatusException.class);
    }

    private static void setId(Consent c, UUID id) {
        try {
            var f = Consent.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(c, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
