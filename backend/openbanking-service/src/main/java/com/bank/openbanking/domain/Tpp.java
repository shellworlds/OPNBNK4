package com.bank.openbanking.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tpps")
public class Tpp {

    @Id
    private UUID id;

    @Column(name = "external_id", nullable = false, unique = true, length = 128)
    private String externalId;

    @Column(nullable = false)
    private String name;

    @Column(name = "redirect_uri", length = 512)
    private String redirectUri;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected Tpp() {}

    public Tpp(UUID id, String externalId, String name, String redirectUri) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
        this.redirectUri = redirectUri;
    }

    public String getExternalId() {
        return externalId;
    }
}
