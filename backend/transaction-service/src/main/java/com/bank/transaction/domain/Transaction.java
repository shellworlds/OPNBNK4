package com.bank.transaction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private TransactionMovementType type;

    @Column(length = 512)
    private String description;

    @Column(length = 256)
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TransactionStatus status = TransactionStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected Transaction() {}

    public Transaction(
            UUID accountId,
            BigDecimal amount,
            String currency,
            TransactionMovementType type,
            String description,
            String reference,
            TransactionStatus status) {
        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.description = description;
        this.reference = reference;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public TransactionMovementType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getReference() {
        return reference;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
