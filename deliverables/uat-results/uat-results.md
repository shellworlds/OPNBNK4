# UAT results — Day 5 (simulated stakeholder run)

**Run date:** 2026-03-23  
**Environment:** Local Docker Compose + automated API checks  
**Executed by:** Engineering (simulated UAT)

## Summary

| Test case | Status | Notes |
|-----------|--------|--------|
| 1 — Customer onboarding | **Partial PASS** | Keycloak + dashboard OK; FIDO2 enrollment documented as future |
| 2 — Account management | **Partial PASS** | Transactions OK; PDF statement and profile update stubs |
| 3 — Payments | **PASS** | Normal complete = `COMPLETED`; EUR **> 5000** = `UNDER_REVIEW`; very high = block |
| 4 — Open banking | **PASS** | AIS/PIS/revoke covered by Playwright + smoke; demo headers |
| 5 — Admin | **Partial PASS** | Grafana/Prometheus/Jaeger up; CB verified manually |
| 6 — Security | **Partial PASS** | Keycloak brute-force configurable; JWT not enforced on all Compose routes |

## Evidence (logs / automation)

- CI: Gradle tests, Postgres IT, Playwright API E2E (includes high-value fraud case).
- Manual: `docker compose up`, Grafana at `http://localhost:3001`, Jaeger at `http://localhost:16686`.
- Script reference: `infrastructure/scripts/smoke-e2e.py`, `tests/e2e/api/banking-api.spec.ts`.

## Issues found

| ID | Severity | Description | Fix plan |
|----|----------|-------------|----------|
| UAT-1 | Low | Statement PDF not implemented | Day 6+ reporting service |
| UAT-2 | Medium | FIDO2 not end-to-end in portal | Track `docs/security/fido2.md` |
| UAT-3 | Low | Date filter on transaction list UI | Frontend enhancement |

## Sign-off

Stakeholder approval is recorded in **`docs/uat/sign-off-placeholder.md`** (template). Replace with scanned PDF or linked ticket when available.
