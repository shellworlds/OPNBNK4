# Day 2 — Delivery summary

← [Home](Home)

**Objective:** Core business logic, PostgreSQL migrations, event-style integration (Kafka/outbox), open banking AIS/PIS and consent, gateway hardening (CORS, Redis rate limit for open banking in Docker), Keycloak in Compose, React portal with Keycloak JS and axios, CI expansion, and client-facing submission docs.

## Delivered in repository

1. **Persistence** — Flyway migrations per service (`accounts`, `account_holders`; `transactions`, `transaction_events`; `tpps`, `consents`).
2. **Account service** — JPA with `@Version` optimistic locking; REST CRUD and balance adjustment; optional Kafka consumer for completed transactions.
3. **Transaction service** — Transaction lifecycle, outbox publisher, WebClient to account service, Kafka topics for created/completed events.
4. **Open banking** — Consent API, AIS/PIS routes, consent validation filter (JWT scopes or demo headers), downstream calls to core services.
5. **API gateway** — Routes `/api/accounts/**`, `/api/transactions/**`, `/openbanking/**`; global CORS; Docker profile Redis `RequestRateLimiter` on `/openbanking/**`.
6. **Platform** — Docker Compose: Kafka, Zookeeper, Kafka UI, Redis, Keycloak (realm import), Postgres, all services.
7. **Frontend** — Keycloak adapter when configured; axios + Bearer; dashboard by customer id; account detail with transactions; production nginx image.
8. **Quality** — JUnit, MockMvc, Postgres Testcontainers, gateway WireMock, React tests; CI job `java-postgres-integration`; E2E smoke script.
9. **Docs** — Updated architecture/security wiki sources, FIDO2 placeholder, `DAY2_COMPLETE.md`, **client handoff** [`docs/submission/CLIENT_HANDOFF_DAY2.md`](https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY2.md).

## Out of scope / follow-ups (Day 3+)

JWT required on all core APIs in Compose (smoke today is unauthenticated on those paths), full Kafka+Keycloak Testcontainers matrix, published OpenAPI artifacts, Dependabot enablement — see [DAY2_COMPLETE.md](https://github.com/shellworlds/OPNBNK4/blob/main/DAY2_COMPLETE.md) and [DAY2_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY2_DEDICATED_ISSUES.md).

## References (repo)

- [README](https://github.com/shellworlds/OPNBNK4/blob/main/README.md)
- [Client handoff Day 2](https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY2.md)
- [DAY2_COMPLETE.md](https://github.com/shellworlds/OPNBNK4/blob/main/DAY2_COMPLETE.md)
