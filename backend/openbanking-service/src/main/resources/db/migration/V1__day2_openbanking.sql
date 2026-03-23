CREATE TABLE tpps (
    id UUID NOT NULL PRIMARY KEY,
    external_id VARCHAR(128) NOT NULL UNIQUE,
    name VARCHAR(256) NOT NULL,
    redirect_uri VARCHAR(512),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE consents (
    id UUID NOT NULL PRIMARY KEY,
    customer_id VARCHAR(128) NOT NULL,
    tpp_external_id VARCHAR(128) NOT NULL REFERENCES tpps (external_id),
    permissions TEXT NOT NULL,
    status VARCHAR(16) NOT NULL,
    valid_from TIMESTAMP NOT NULL,
    valid_to TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_consent_status CHECK (status IN ('ACTIVE', 'REVOKED', 'EXPIRED'))
);

CREATE INDEX idx_consents_tpp_customer ON consents (tpp_external_id, customer_id);

INSERT INTO tpps (id, external_id, name, redirect_uri)
VALUES (
        '00000000-0000-4000-8000-0000000000a1',
        'tpp-aisp-demo',
        'Demo AISP TPP',
        'http://localhost:3000/callback'
    );
