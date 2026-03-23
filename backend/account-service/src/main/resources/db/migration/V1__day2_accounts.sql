CREATE TABLE accounts (
    id UUID NOT NULL PRIMARY KEY,
    customer_id VARCHAR(128) NOT NULL,
    account_number VARCHAR(34) NOT NULL UNIQUE,
    account_type VARCHAR(16) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    balance NUMERIC(19, 4) NOT NULL DEFAULT 0,
    status VARCHAR(16) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_accounts_type CHECK (account_type IN ('CHECKING', 'SAVINGS')),
    CONSTRAINT chk_accounts_status CHECK (status IN ('ACTIVE', 'FROZEN', 'CLOSED'))
);

CREATE INDEX idx_accounts_customer ON accounts (customer_id);

CREATE TABLE account_holders (
    id UUID NOT NULL PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES accounts (id) ON DELETE CASCADE,
    customer_id VARCHAR(128) NOT NULL,
    role VARCHAR(16) NOT NULL,
    CONSTRAINT chk_holders_role CHECK (role IN ('PRIMARY', 'JOINT'))
);

CREATE INDEX idx_account_holders_account ON account_holders (account_id);
