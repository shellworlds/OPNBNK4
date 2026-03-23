ALTER TABLE transactions DROP CONSTRAINT IF EXISTS chk_tx_status;
ALTER TABLE transactions
  ADD CONSTRAINT chk_tx_status CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED', 'UNDER_REVIEW'));
