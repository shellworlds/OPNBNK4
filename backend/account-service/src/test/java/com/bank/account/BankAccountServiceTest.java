package com.bank.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bank.account.domain.BankAccount;
import com.bank.account.repository.BankAccountRepository;
import com.bank.account.service.BankAccountService;
import com.bank.account.web.dto.CreateAccountRequest;
import java.math.BigDecimal;
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
class BankAccountServiceTest {

    @Mock
    private BankAccountRepository accounts;

    @InjectMocks
    private BankAccountService service;

    @Test
    void createPersistsNewAccount() {
        when(accounts.findByIban("GB82WEST12345698765432")).thenReturn(Optional.empty());
        var saved = new BankAccount("GB82WEST12345698765432", "GBP", new BigDecimal("10"), "c1");
        when(accounts.save(any(BankAccount.class))).thenAnswer(inv -> inv.getArgument(0));

        var req = new CreateAccountRequest("GB82WEST12345698765432", "gbp", "c1", new BigDecimal("10"));
        var res = service.create(req);

        assertThat(res.iban()).isEqualTo("GB82WEST12345698765432");
        assertThat(res.currency()).isEqualTo("GBP");
        verify(accounts).save(any(BankAccount.class));
    }

    @Test
    void createRejectsDuplicateIban() {
        when(accounts.findByIban("GB82WEST12345698765432"))
                .thenReturn(Optional.of(new BankAccount("GB82WEST12345698765432", "GBP", BigDecimal.ONE, "x")));

        var req = new CreateAccountRequest("GB82WEST12345698765432", "GBP", "c1", BigDecimal.ZERO);

        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("statusCode", org.springframework.http.HttpStatus.CONFLICT);
    }

    @Test
    void listAllMapsResponses() {
        when(accounts.findAll())
                .thenReturn(
                        List.of(new BankAccount("GB11AAAA11111111111111", "GBP", BigDecimal.ONE, "a")));
        assertThat(service.listAll()).hasSize(1);
    }

    @Test
    void getByIdThrowsWhenMissing() {
        UUID id = UUID.randomUUID();
        when(accounts.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(id)).isInstanceOf(ResponseStatusException.class);
    }
}
