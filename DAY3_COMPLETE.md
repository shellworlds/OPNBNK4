# Day 3 completion summary

**Client submission (share with stakeholders):** [docs/submission/CLIENT_HANDOFF_DAY3.md](docs/submission/CLIENT_HANDOFF_DAY3.md) — canonical URL: https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY3.md  

**Quick link:** [SUBMISSION_DAY3.md](SUBMISSION_DAY3.md)  

**Verification outputs:** [docs/submission/VERIFICATION_RESULTS_DAY3.md](docs/submission/VERIFICATION_RESULTS_DAY3.md)

**GitHub issues checklist:** [docs/github/DAY3_DEDICATED_ISSUES.md](docs/github/DAY3_DEDICATED_ISSUES.md)

## Delivered in this increment

- **Transaction ↔ fraud**: Synchronous `FraudEvaluationClient` before completion; `FRAUD_EVALUATION_ENABLED` / `FRAUD_EVALUATION_BASE_URL`; optional `X-Device-Id` header.
- **Account ↔ core simulator**: `CoreSimulatorClient` with Resilience4j circuit breaker; optional `coreLedgerBookBalance` and `coreCustomerDisplayName` on `AccountResponse`; Redis-backed `@Cacheable` for `getAccount` (60s TTL) with eviction on balance changes (Docker).
- **API gateway**: Reactor Resilience4j circuit breakers and `/fallback/*` JSON responses; optional **channel header** validation (`X-Client-Id`, `X-Client-Channel`) — **enabled in Docker** via `GATEWAY_CHANNEL_VALIDATION_ENABLED`.
- **Compose**: `core-simulator`, `fraud-detection-service`, `notification-service`; wired env vars for account Redis/cache/core and transaction fraud.
- **Indexes**: Flyway `V2` for `transactions(account_id, created_at DESC)` and `consents(customer_id, status)`.
- **Smoke script**: Sends default channel headers for gateway validation.
- **Fraud ML stub**: `POST /api/fraud/ml/predict`.
- **Docs**: `docs/security/fraud-detection.md`; Day 3 section in `docs/architecture/high-level-design.md`.

## Verification

**Recorded results:** see [docs/submission/VERIFICATION_RESULTS_DAY3.md](docs/submission/VERIFICATION_RESULTS_DAY3.md).

```bash
docker compose up --build -d
python3 infrastructure/scripts/smoke-e2e.py --gateway http://localhost:8080
docker compose down -v --remove-orphans
```

**Docker note:** services using `includeBuild("../shared-libs/events")` build with Compose **`context: ./backend`** and the updated Dockerfiles under each service folder.

To exercise a **blocked** transaction, create a pending transaction with amount **> €50,000** equivalent (per `RuleEngine`) and call `POST /complete` with channel headers.

## Blockers / follow-ups for Day 4+

- **WebAuthn / FIDO2** backend and web portal UI (`@simplewebauthn/browser`) — not implemented in this commit.
- **Vault / Azure Key Vault** secret injection across services — not wired; add Compose `vault` dev mode and refactor credentials when ready.
- **Gateway Redis response cache** for read-only routes — account-service already caches; gateway-level cache still optional.
- **Aggregated OpenAPI `/docs`**, **developer portal**, **JMeter plan**, **Prometheus/Grafana/ELK**, **CI GHCR** — pending.
- **Full `docker compose` run** in CI/agent may require Docker resources; run locally to confirm FIDO once WebAuthn lands.

## Suggested Day 4 focus

End-to-end and load tests, complete auth/WebAuthn path, observability stack, consolidated API docs, and production deployment hardening.
