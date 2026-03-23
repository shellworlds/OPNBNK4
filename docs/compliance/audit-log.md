# Audit logging

- **Transaction events:** `transaction_events` table stores `TRANSACTION_CREATED`, `TRANSACTION_COMPLETED`, `TRANSACTION_FRAUD_REVIEW`.
- **Kafka:** domain events for downstream consumers (fraud async, notifications).
- **Application logs:** structured logging recommended; ship to Log Analytics in Azure.

Tamper-evident central log store and retention policies are an operational choice for production.
