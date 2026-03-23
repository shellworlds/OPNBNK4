package com.bank.account.repository;

import com.bank.account.domain.Account;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByCustomerId(String customerId);

    Optional<Account> findByAccountNumber(String accountNumber);
}
