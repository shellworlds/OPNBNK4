CREATE TABLE transactions (
    id UUID NOT NULL PRIMARY KEY,
    account_id UUID NOT NULL,
    amount NUMERIC(19, 4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    type VARCHAR(8) NOT NULL,
    description VARCHAR(512),
    reference VARCHAR(256),
    status VARCHAR(16) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_tx_type CHECK (type IN ('CREDIT', 'DEBIT')),
    CONSTRAINT chk_tx_status CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED'))
);

CREATE INDEX idx_transactions_account ON transactions (account_id);

CREATE TABLE transaction_events (
    id UUID NOT NULL PRIMARY KEY,
    aggregate_id UUID NOT NULL,
    event_type VARCHAR(64) NOT NULL,
    payload TEXT NOT NULL,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tx_events_unpublished ON transaction_events (published, created_at);
