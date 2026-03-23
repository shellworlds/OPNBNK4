# Verification results (reproducible)

This document records what was executed to validate the platform with **public-domain sample data** (see `infrastructure/fixtures/public-banking-samples.json`) and automated tests.

## Open sample data (what it is)

The fixture file contains **IBAN-style strings commonly published as format / check-digit examples** (UK, DE, FR patterns) plus illustrative transaction descriptions and demo TPP consent rows. These are **not real bank accounts** and hold no funds. References are listed in the JSON (`license_note`, `references`).

## Automated tests (last run: 2026-03-23)

**Raw logs:** [`reports/day1-verification-2026-03-23/`](reports/day1-verification-2026-03-23/README.md) · **Submission summary:** [`day1-complete-report.md`](day1-complete-report.md)

| Scope | Count | Notes |
|-------|------:|--------|
| `backend/account-service` | **14** | Includes **Testcontainers PostgreSQL** `AccountPostgresIntegrationTest` loading the public fixture |
| `backend/transaction-service` | **8** | Includes **Testcontainers** `TransactionPostgresIntegrationTest` using fixture transaction rows |
| `backend/openbanking-service` | **8** | Includes **Testcontainers** `OpenbankingPostgresIntegrationTest` using fixture consents |
| `backend/api-gateway` | **3** | WireMock route IT + filter unit test + context smoke |
| `frontend/web-portal` | **4** | React Testing Library (App, Login, Dashboard ×2) |

**Total backend JUnit test cases:** 33  
**Frontend:** 4

### Commands used

```bash
# Backend (Docker required for Testcontainers to pull postgres:16-alpine)
for s in account-service transaction-service openbanking-service api-gateway; do
  (cd backend/$s && ./gradlew test --no-daemon)
done

cd frontend/web-portal && npm test -- --watchAll=false
```

CI runs the same Gradle and npm commands; Java jobs set `TESTCONTAINERS_RYUK_DISABLED=true` for stability on GitHub-hosted runners.

## End-to-end smoke (live stack)

With **Docker Compose** running and ports **8080** (gateway) and **3000** (optional UI) available:

```bash
docker compose up -d --build
python3 infrastructure/scripts/smoke-e2e.py --gateway http://localhost:8080
```

The script:

1. Waits for `GET {gateway}/actuator/health`
2. Waits until `GET {gateway}/api/accounts` returns **200** (gateway + downstream services)
3. Posts each account from the public fixture via the gateway
4. Posts transactions against the **first returned account id**
5. Posts consents from the fixture
6. Prints a JSON summary to stdout

A shell wrapper is available: `infrastructure/scripts/smoke-e2e.sh` (uses `GATEWAY` env, default `http://localhost:8080`).

**Note:** On this developer machine, `docker compose up` can fail if host ports are already bound (e.g. **5432** for Postgres) or if Docker cannot recreate old containers (permission / daemon state). Free the ports or stop conflicting stacks, then re-run. **Redis and Kafka are not published to the host** by default (in-stack only) to reduce port clashes.

## GitHub Actions

Workflow `.github/workflows/ci.yml` includes job **`e2e-open-data-smoke`**: builds images, runs `docker compose up -d --build`, runs `python3 infrastructure/scripts/smoke-e2e.py`, then `docker compose down -v`.
