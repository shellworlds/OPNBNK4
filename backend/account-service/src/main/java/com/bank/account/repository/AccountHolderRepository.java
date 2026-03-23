package com.bank.account.repository;

import com.bank.account.domain.AccountHolder;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, UUID> {}
