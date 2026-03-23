package com.bank.account.web;

import com.bank.account.service.AccountService;
import com.bank.account.web.dto.AccountResponse;
import com.bank.account.web.dto.BalanceAdjustRequest;
import com.bank.account.web.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable UUID id) {
        return accountService.getAccount(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<AccountResponse> listForCustomer(@PathVariable String customerId) {
        return accountService.listForCustomer(customerId);
    }

    @PostMapping("/{id}/balance")
    public AccountResponse adjustBalance(@PathVariable UUID id, @Valid @RequestBody BalanceAdjustRequest request) {
        return accountService.adjustBalance(id, request);
    }
}
