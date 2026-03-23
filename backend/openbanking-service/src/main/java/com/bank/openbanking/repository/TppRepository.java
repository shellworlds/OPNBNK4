package com.bank.openbanking.repository;

import com.bank.openbanking.domain.Tpp;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TppRepository extends JpaRepository<Tpp, UUID> {

    Optional<Tpp> findByExternalId(String externalId);
}
