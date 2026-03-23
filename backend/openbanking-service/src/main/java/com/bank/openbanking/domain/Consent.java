package com.bank.openbanking.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "openbanking_consents")
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 128)
    private String tppId;

    @Column(nullable = false, length = 512)
    private String scopes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ConsentStatus status = ConsentStatus.ACTIVE;

    @Column(nullable = false, length = 128)
    private String customerId;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column
    private Instant validUntil;

    protected Consent() {}

    public Consent(String tppId, String scopes, String customerId, Instant validUntil) {
        this.tppId = tppId;
        this.scopes = scopes;
        this.customerId = customerId;
        this.validUntil = validUntil;
    }

    public UUID getId() {
        return id;
    }

    public String getTppId() {
        return tppId;
    }

    public String getScopes() {
        return scopes;
    }

    public ConsentStatus getStatus() {
        return status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setStatus(ConsentStatus status) {
        this.status = status;
    }
}
