package com.bank.account.repository;

import com.bank.account.domain.BankAccount;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    Optional<BankAccount> findByIban(String iban);

    List<BankAccount> findByCustomerIdOrderByCreatedAtDesc(String customerId);
}
