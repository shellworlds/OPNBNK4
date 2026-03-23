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
@Table(name = "consents")
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_id", nullable = false, length = 128)
    private String customerId;

    @Column(name = "tpp_external_id", nullable = false, length = 128)
    private String tppExternalId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String permissions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ConsentStatus status = ConsentStatus.ACTIVE;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom = Instant.now();

    @Column(name = "valid_to")
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected Consent() {}

    public Consent(
            String customerId, String tppExternalId, String permissions, Instant validFrom, Instant validTo) {
        this.customerId = customerId;
        this.tppExternalId = tppExternalId;
        this.permissions = permissions;
        this.validFrom = validFrom != null ? validFrom : Instant.now();
        this.validTo = validTo;
    }

    public UUID getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getTppExternalId() {
        return tppExternalId;
    }

    public String getPermissions() {
        return permissions;
    }

    public ConsentStatus getStatus() {
        return status;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setStatus(ConsentStatus status) {
        this.status = status;
    }
}
