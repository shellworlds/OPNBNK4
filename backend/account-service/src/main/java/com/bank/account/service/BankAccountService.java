package com.bank.account.service;

import com.bank.account.domain.BankAccount;
import com.bank.account.repository.BankAccountRepository;
import com.bank.account.web.dto.AccountResponse;
import com.bank.account.web.dto.CreateAccountRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BankAccountService {

    private final BankAccountRepository accounts;

    public BankAccountService(BankAccountRepository accounts) {
        this.accounts = accounts;
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> listAll() {
        return accounts.findAll().stream().map(AccountResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AccountResponse getById(UUID id) {
        return accounts
                .findById(id)
                .map(AccountResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    @Transactional
    public AccountResponse create(CreateAccountRequest request) {
        accounts.findByIban(request.iban()).ifPresent(a -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "IBAN already exists");
        });
        var entity = new BankAccount(
                request.iban().trim().toUpperCase(),
                request.currency().trim().toUpperCase(),
                request.initialBalance(),
                request.customerId().trim());
        return AccountResponse.from(accounts.save(entity));
    }
}
