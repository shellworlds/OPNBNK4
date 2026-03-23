# Day 2 — Core implementation and open banking foundation

## Completed

- **Persistence**: Flyway migrations for `account-service`, `transaction-service`, and `openbanking-service` (PostgreSQL schemas as specified on Day 2).
- **Account service**: JPA entities with optimistic locking on balance, REST API, optional Kafka consumer for `TransactionCompleted` when `day2.kafka.consumer.enabled=true`.
- **Transaction service**: Transactions + outbox-style `transaction_events`, Kafka producer (scheduled outbox publisher when enabled), WebClient to account service, REST including `POST .../complete`.
- **Shared events**: JSON-friendly records in `backend/shared-libs/events`.
- **Open banking**: Consent CRUD, AIS and PIS controllers, consent validation filter (JWT scopes or demo headers), Flyway seed TPP.
- **API gateway**: Routes for `/api/accounts/**`, `/api/transactions/**`, `/openbanking/**`; global CORS; pass-through of client headers (including `Authorization`). **Docker profile**: Redis-backed rate limiting on `/openbanking/**`.
- **Docker Compose**: Zookeeper, Kafka, Kafka UI, Redis, Keycloak (realm import), Postgres, all services, gateway, **web-portal** (multi-stage nginx build). Core services intentionally run **without** `KEYCLOAK_ISSUER_URI` so automated smoke does not need JWTs.
- **Frontend**: Keycloak JS when configured; axios layer with Bearer interceptor; dashboard loads `/api/accounts/customer/{id}` via `REACT_APP_MOCK_CUSTOMER_ID`; account detail + transactions; Jest allows transforming `keycloak-js` ESM.
- **Docs**: Updated `docs/architecture/open-banking-strategy.md`, `docs/architecture/security-overview.md`, added `docs/security/fido2.md`.
- **Smoke**: `infrastructure/scripts/smoke-e2e.py` aligned with Day 2 payloads and consent customer matching.

## Known gaps / follow-ups (Day 3+)

- **JWT everywhere**: Enable `KEYCLOAK_ISSUER_URI` on account, transaction, and open banking services in Compose once smoke and tests issue real tokens (or use WireMock / test realm clients in CI).
- **End-to-end Kafka + consumer**: Verify dual path (REST balance update vs consumer) under load; add idempotent consumer processing keys if needed.
- **OpenAPI**: Publish versioned specs under `docs/api/` and enforce in CI.
- **Testcontainers**: Postgres-backed tests run in CI matrix; a **dedicated** job re-runs `*Postgres*IntegrationTest` patterns. Full **Kafka + Keycloak** Testcontainers matrix for every service is not yet implemented (heavyweight in GitHub Actions).
- **Gateway rate limit**: Only active with `SPRING_PROFILES_ACTIVE=docker` on the gateway; local `./gradlew bootRun` without Redis remains without Redis rate limiting by design.
- **List all accounts**: `GET /api/accounts` is convenient for smoke; restrict or remove in hardened environments.

## Suggested Day 3 focus

- Contract tests between gateway and services; OpenAPI generation.
- OAuth2 resource server enabled in docker with service accounts for smoke, or token acquisition step in `smoke-e2e.py`.
- FIDO2 spike or Keycloak WebAuthn configuration (see `docs/security/fido2.md`).
