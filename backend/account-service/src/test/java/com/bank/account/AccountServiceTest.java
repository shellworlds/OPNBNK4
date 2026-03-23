package com.bank.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bank.account.domain.Account;
import com.bank.account.domain.AccountHolder;
import com.bank.account.integration.CoreSimulatorClient;
import com.bank.account.domain.AccountStatus;
import com.bank.account.domain.AccountType;
import com.bank.account.repository.AccountHolderRepository;
import com.bank.account.repository.AccountRepository;
import com.bank.account.service.AccountService;
import com.bank.account.web.dto.CreateAccountRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accounts;

    @Mock
    private AccountHolderRepository holders;

    @Mock
    private ObjectProvider<CoreSimulatorClient> coreSimulatorClient;

    private AccountService service;

    @BeforeEach
    void setUp() {
        service = new AccountService(accounts, holders, coreSimulatorClient);
    }

    @Test
    void createPersistsNewAccount() {
        when(accounts.findByAccountNumber("GB82WEST12345698765432")).thenReturn(Optional.empty());
        when(accounts.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        var req = new CreateAccountRequest(
                "c1", "GB82WEST12345698765432", AccountType.CHECKING, "gbp", new BigDecimal("10"));
        var res = service.createAccount(req);

        assertThat(res.accountNumber()).isEqualTo("GB82WEST12345698765432");
        assertThat(res.currency()).isEqualTo("GBP");
        verify(accounts).save(any(Account.class));
        verify(holders).save(any(AccountHolder.class));
    }

    @Test
    void createRejectsDuplicateAccountNumber() {
        when(accounts.findByAccountNumber("GB82WEST12345698765432"))
                .thenReturn(Optional.of(new Account("x", "GB82WEST12345698765432", AccountType.CHECKING, "GBP", BigDecimal.ONE, AccountStatus.ACTIVE)));

        var req = new CreateAccountRequest("c1", "GB82WEST12345698765432", AccountType.CHECKING, "GBP", BigDecimal.ZERO);

        assertThatThrownBy(() -> service.createAccount(req))
                .isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("statusCode", org.springframework.http.HttpStatus.CONFLICT);
    }

    @Test
    void listForCustomerMapsResponses() {
        when(accounts.findByCustomerId("a"))
                .thenReturn(List.of(new Account("a", "GB11AAAA11111111111111", AccountType.CHECKING, "GBP", BigDecimal.ONE, AccountStatus.ACTIVE)));
        assertThat(service.listForCustomer("a")).hasSize(1);
    }

    @Test
    void getAccountThrowsWhenMissing() {
        UUID id = UUID.randomUUID();
        when(accounts.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getAccount(id)).isInstanceOf(ResponseStatusException.class);
    }

}
