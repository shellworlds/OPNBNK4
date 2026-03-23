CREATE INDEX IF NOT EXISTS idx_transactions_account_created ON transactions (account_id, created_at DESC);
