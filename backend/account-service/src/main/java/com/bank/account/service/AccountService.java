package com.bank.account.service;

import com.bank.account.domain.Account;
import com.bank.account.domain.AccountHolder;
import com.bank.account.domain.AccountStatus;
import com.bank.account.domain.HolderRole;
import com.bank.account.repository.AccountHolderRepository;
import com.bank.account.repository.AccountRepository;
import com.bank.account.web.dto.AccountResponse;
import com.bank.account.web.dto.BalanceAdjustRequest;
import com.bank.account.web.dto.CreateAccountRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountHolderRepository accountHolderRepository;

    public AccountService(AccountRepository accountRepository, AccountHolderRepository accountHolderRepository) {
        this.accountRepository = accountRepository;
        this.accountHolderRepository = accountHolderRepository;
    }

    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        if (accountRepository.findByAccountNumber(request.accountNumber()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account number already exists");
        }
        Account account = new Account(
                request.customerId(),
                request.accountNumber(),
                request.accountType(),
                request.currency().toUpperCase(),
                request.initialBalance(),
                AccountStatus.ACTIVE);
        account = accountRepository.save(account);
        accountHolderRepository.save(new AccountHolder(account, request.customerId(), HolderRole.PRIMARY));
        return AccountResponse.from(account);
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccount(UUID id) {
        return accountRepository
                .findById(id)
                .map(AccountResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> listForCustomer(String customerId) {
        return accountRepository.findByCustomerId(customerId).stream().map(AccountResponse::from).toList();
    }

    /** Convenience listing for gateway health/smoke (not for production exposure without auth). */
    @Transactional(readOnly = true)
    public List<AccountResponse> listAll() {
        return accountRepository.findAll().stream().map(AccountResponse::from).toList();
    }

    @Transactional
    public AccountResponse adjustBalance(UUID id, BalanceAdjustRequest request) {
        return AccountResponse.from(applyBalanceMovement(id, request.amount(), request.type(), request.currency()));
    }

    /**
     * Used by REST and Kafka consumer. Amount is always positive; type CREDIT adds, DEBIT subtracts.
     */
    @Transactional
    public Account applyBalanceMovement(UUID accountId, BigDecimal amount, String type, String currency) {
        try {
            Account account = accountRepository
                    .findById(accountId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
            if (account.getStatus() != AccountStatus.ACTIVE) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Account not active");
            }
            if (!account.getCurrency().equalsIgnoreCase(currency)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Currency mismatch");
            }
            String t = type.toUpperCase();
            BigDecimal delta;
            if ("CREDIT".equals(t)) {
                delta = amount;
            } else if ("DEBIT".equals(t)) {
                delta = amount.negate();
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "type must be CREDIT or DEBIT");
            }
            BigDecimal next = account.getBalance().add(delta);
            if (next.compareTo(BigDecimal.ZERO) < 0) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient funds");
            }
            account.setBalance(next);
            return accountRepository.save(account);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Concurrent balance update — retry", e);
        }
    }
}
