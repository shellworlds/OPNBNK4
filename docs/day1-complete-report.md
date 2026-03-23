# Day 1 — Complete programme & verification report (submission)

**Project:** OPNBNK4 — Digital Banking Platform (microservices, open-banking alignment, omni-channel foundation)  
**Repository:** [github.com/shellworlds/OPNBNK4](https://github.com/shellworlds/OPNBNK4)  
**Release tag:** [v0.1.0-day1](https://github.com/shellworlds/OPNBNK4/releases/tag/v0.1.0-day1)  
**Report date:** 23 March 2026  
**Git revision verified:** `c3e54df` (see artifact bundle for exact full SHA)

This document summarises **what was delivered in Day 1**, **what was verified in the latest full test run**, and **where to find evidence** (files in-repo and URLs on GitHub).

---

## 1. Executive summary

| Item | Status |
|------|--------|
| Day 1 scope (scaffold, core APIs, gateway, web portal, CI, docs) | **Complete** |
| Backend `./gradlew clean test` (4 modules) | **BUILD SUCCESSFUL** (33 JUnit cases) |
| Frontend `npm test` + `npm run build` | **Pass** (4 tests; production bundle OK) |
| Raw command logs bundled for submission | **Yes** — [`docs/reports/day1-verification-2026-03-23/`](reports/day1-verification-2026-03-23/README.md) |

**Note:** On some Linux hosts, Testcontainers may log **non-fatal** Docker cleanup warnings (`permission denied` removing Postgres containers) **after** tests complete. Gradle still exited **BUILD SUCCESSFUL** for all database-backed services in this run. CI on GitHub Actions typically shows clean teardown.

---

## 2. Programme accomplishments (Day 1)

- **Monorepo:** `backend/` (account-service, transaction-service, openbanking-service, api-gateway), `frontend/web-portal`, `infrastructure/`, `docs/`, `.github/workflows/`, root `docker-compose.yml`.
- **Backend:** Spring Boot 3, JPA REST for accounts, ledger-style transactions, open-banking consents; Spring Cloud Gateway with routing and request logging; Gradle per service; Dockerfiles.
- **Data & samples:** PostgreSQL in Compose; public IBAN-style fixtures in `infrastructure/fixtures/public-banking-samples.json` (used by Testcontainers integration tests — not live customer data).
- **Frontend:** React portal with routing, mock login, dashboard calling the gateway; automated tests (RTL).
- **Quality:** JUnit (slices, unit, MockMvc, H2 full-stack, PostgreSQL via Testcontainers), gateway WireMock IT + filter test; `docs/verification.md` for reproducibility.
- **CI/CD:** [GitHub Actions — CI workflow](https://github.com/shellworlds/OPNBNK4/actions/workflows/ci.yml) on push/PR; manual **Day 1 verify** workflow; optional E2E smoke job; same Gradle/npm patterns as locally.
- **Product / collaboration:** [Repository Projects](https://github.com/shellworlds/OPNBNK4/projects) linked to board [OPNBNK4 Digital Banking Platform](https://github.com/users/shellworlds/projects/6); [Issues](https://github.com/shellworlds/OPNBNK4/issues); [Wiki](https://github.com/shellworlds/OPNBNK4/wiki) (mirror under `docs/wiki/`).
- **Release artefact:** [CHANGELOG](https://github.com/shellworlds/OPNBNK4/blob/main/CHANGELOG.md), GitHub Release **v0.1.0-day1**.

**Explicitly not Day 1:** production OAuth2 hardening, Kafka in business logic, full mobile app, production cloud hardening, full E2E against Compose on every developer machine (script + CI job provided).

---

## 3. Verification run (23 March 2026) — results

Commands executed (repository root):

```bash
export TESTCONTAINERS_RYUK_DISABLED=true
for s in account-service transaction-service openbanking-service api-gateway; do
  (cd backend/$s && ./gradlew clean test --no-daemon)
done
cd frontend/web-portal && npm test -- --watchAll=false && npm run build
```

### 3.1 Outcome table

| Step | Result | Evidence in bundle |
|------|--------|---------------------|
| account-service | **BUILD SUCCESSFUL** in ~44s | `gradle-account-service.log` |
| transaction-service | **BUILD SUCCESSFUL** in ~44s | `gradle-transaction-service.log` |
| openbanking-service | **BUILD SUCCESSFUL** in ~44s | `gradle-openbanking-service.log` |
| api-gateway | **BUILD SUCCESSFUL** in ~12s | `gradle-api-gateway.log` |
| web-portal tests | **4 passed**, 3 suites | `npm-test.log` |
| web-portal build | **Compiled successfully** | `npm-build.log` |

### 3.2 Test counts (JUnit XML + Jest)

| Module | Cases |
|--------|------:|
| account-service | 14 |
| transaction-service | 8 |
| openbanking-service | 8 |
| api-gateway | 3 |
| **Backend total** | **33** |
| frontend/web-portal | **4** |

Source: `junit-counts.txt` and `npm-test.log` in [`docs/reports/day1-verification-2026-03-23/`](reports/day1-verification-2026-03-23/README.md).

---

## 4. Submission package — files and URLs

### 4.1 Attach or point reviewers to

| Deliverable | Location |
|-------------|----------|
| **This report** | [`docs/day1-complete-report.md`](day1-complete-report.md) (this file) |
| **Detailed test narrative** | [`docs/day1-client-test-report.md`](day1-client-test-report.md) |
| **Repro commands & counts** | [`docs/verification.md`](verification.md) |
| **Raw logs (zip this folder if required)** | [`docs/reports/day1-verification-2026-03-23/`](reports/day1-verification-2026-03-23/README.md) |

### 4.2 Public URLs (no login required for read-only)

| Resource | URL |
|----------|-----|
| Repository | https://github.com/shellworlds/OPNBNK4 |
| Release v0.1.0-day1 | https://github.com/shellworlds/OPNBNK4/releases/tag/v0.1.0-day1 |
| Actions (CI history) | https://github.com/shellworlds/OPNBNK4/actions |
| Wiki | https://github.com/shellworlds/OPNBNK4/wiki |
| Projects (repo tab) | https://github.com/shellworlds/OPNBNK4/projects |
| Project board (direct) | https://github.com/users/shellworlds/projects/6 |

*Tip:* For “green build” evidence, open **Actions** and select the latest **`CI`** run on `main` after push.

---

## 5. Environment (verification host)

See **`environment.txt`** in the report bundle. Representative: **OpenJDK 21**, **Node 20**, **Docker 28**, **Gradle 8.8** (wrapper).

---

## 6. Conclusion

Day 1 is **closed** from a **delivery and automated quality** perspective: the codebase is structured, core services and the gateway are implemented and tested, the web portal builds and tests cleanly, CI is wired, and documentation plus this verification bundle support **formal submission**.

**Sign-off line for packs:** *Day 1 objectives met; 33 backend + 4 frontend automated tests passed on 2026-03-23; logs archived under `docs/reports/day1-verification-2026-03-23/`; tag v0.1.0-day1 on GitHub.*

---

*Prepared automatically from a full local test run and repository state on 23 March 2026.*
