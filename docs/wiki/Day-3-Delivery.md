# Day 3 — Delivery summary

← [Home](Home)

**Objective:** Omni-channel alignment (mobile app / gateway CORS), **core banking simulator** and **account-service** enrichment with **Resilience4j**, **Redis** cache for account reads, **fraud-detection** (sync + Kafka) and **notification** consumer, gateway **channel headers** and **circuit-breaker fallbacks**, DB **indexes**, docs (fraud, runbook, HLD), CI/Dependabot/wiki/issue hygiene, and **client handoff** for Day 3.

## Delivered in repository

1. **core-simulator** — REST stubs for customer and ledger; consumed by account-service when `core.integration.enabled=true`.
2. **account-service** — `CoreSimulatorClient` with circuit breaker; `@Cacheable` `getAccount` with Redis (Docker); `AccountResponse` core fields.
3. **fraud-detection-service** — Rules + REST evaluate; Kafka listener on transaction created; ML placeholder `POST /api/fraud/ml/predict`.
4. **transaction-service** — Optional fraud client before complete; `X-Device-Id` header.
5. **notification-service** — Kafka stub consumer for completed transactions.
6. **api-gateway** — Resilience4j reactor circuit breakers; `/fallback/*`; optional channel validation (`X-Client-Id`, `X-Client-Channel`).
7. **Compose** — New services wired; smoke script sends channel headers.
8. **Flyway V2** — Indexes on `transactions` and `consents`.
9. **GitHub** — CI matrix extended; `day3-verify.yml`; Dependabot for new Gradle/npm paths; `DAY3_DEDICATED_ISSUES.md`; **client submission** [`docs/submission/CLIENT_HANDOFF_DAY3.md`](https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY3.md).

## Out of scope / follow-ups (Day 4+)

WebAuthn, Vault, aggregated OpenAPI `/docs`, developer portal, JMeter, full observability stack, GHCR — see [DAY3_COMPLETE.md](https://github.com/shellworlds/OPNBNK4/blob/main/DAY3_COMPLETE.md) and [DAY3_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY3_DEDICATED_ISSUES.md).

## References (repo)

- [Client handoff Day 3](https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY3.md)
- [DAY3_COMPLETE.md](https://github.com/shellworlds/OPNBNK4/blob/main/DAY3_COMPLETE.md)
- [GitHub: Project, Issues, Actions & Insights](GitHub-Project-Issues-Actions-Insights)
