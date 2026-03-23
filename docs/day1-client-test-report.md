# Day 1 — Local Test & Build Report (Client Submission)

**Project:** Digital Banking Platform (microservices, open-banking alignment, omni-channel foundation)  
**Repository:** `shellworlds/OPNBNK4`  
**Report date:** 23 March 2026  
**Phase:** Day 1 — environment, scaffolding, architecture, CI/CD foundation, core APIs, automated tests  

---

## 1. Executive summary

Day 1 deliverables were **built and verified locally** on 23 March 2026:

| Area | Result |
|------|--------|
| **Backend (4 modules)** | All modules: **`./gradlew clean test`** → **BUILD SUCCESSFUL** |
| **Automated test cases (JUnit)** | **33** passed (see breakdown below) |
| **Frontend** | **`npm test`** → **4** tests passed; **`npm run build`** → production bundle produced |
| **Sample / open reference data** | Public IBAN-style fixtures used in PostgreSQL integration tests (`infrastructure/fixtures/public-banking-samples.json`) |

All functional assertions completed successfully. After some Testcontainers runs, the Docker daemon logged **non-fatal cleanup warnings** (`permission denied` removing ephemeral Postgres containers); **this did not fail the build** — all Gradle test tasks completed with **BUILD SUCCESSFUL**. For perfectly clean logs, run tests with a Docker environment that allows container removal (e.g. user in `docker` group, or CI on GitHub Actions).

---

## 2. Environment (local verification host)

| Component | Version / note |
|-----------|----------------|
| OpenJDK | 21.0.10 (Ubuntu package) |
| Gradle (wrapper) | 8.8 |
| Node.js (for web portal) | As per project `package.json` / lockfile |
| Docker | Used by **Testcontainers** for PostgreSQL integration tests |

---

## 3. Backend — commands and outcomes

Commands executed (from repository root, each service directory):

```bash
export TESTCONTAINERS_RYUK_DISABLED=true   # optional; aligns with CI
cd backend/account-service      && ./gradlew clean test --no-daemon
cd backend/transaction-service  && ./gradlew clean test --no-daemon
cd backend/openbanking-service  && ./gradlew clean test --no-daemon
cd backend/api-gateway          && ./gradlew clean test --no-daemon
```

### 3.1 Representative Gradle output (excerpt)

**account-service** (representative of successful completion):

```text
> Task :test
...
BUILD SUCCESSFUL in 44s
6 actionable tasks: 6 executed
```

**api-gateway:**

```text
> Task :test
BUILD SUCCESSFUL in 12s
6 actionable tasks: 6 executed
```

**Note:** On this host, Testcontainers occasionally printed a **shutdown thread** message similar to:

```text
InternalServerErrorException: ... cannot remove container ... permission denied
```

This occurred **after** tests finished; Gradle still reported **BUILD SUCCESSFUL** for all three database-backed services.

### 3.2 Test case counts (from JUnit XML reports)

| Module | Test cases |
|--------|------------|
| account-service | **14** |
| transaction-service | **8** |
| openbanking-service | **8** |
| api-gateway | **3** |
| **Total** | **33** |

Coverage types include: JPA repository slices, service unit tests, MockMvc controller tests, full-stack account integration (H2), **PostgreSQL integration tests** driven by public sample fixtures, gateway WireMock route test, and gateway filter unit test.

---

## 4. Frontend — commands and outcomes

```bash
cd frontend/web-portal
npm test -- --watchAll=false
npm run build
```

### 4.1 Unit test summary (excerpt)

```text
PASS src/pages/Login.test.js
PASS src/pages/Dashboard.test.js
PASS src/App.test.js

Test Suites: 3 passed, 3 total
Tests:       4 passed, 4 total
Time:        ~0.9 s
```

*(Console warnings from React Router v7 migration flags are expected and do not fail the suite.)*

### 4.2 Production build (excerpt)

```text
The build folder is ready to be deployed.
```

---

## 5. Day 1 scope alignment (what was validated)

- **Repository structure** — present and buildable.  
- **Core REST + JPA services** — compile and pass automated tests.  
- **API gateway** — tests pass; routing validated in tests.  
- **Web portal** — tests pass; static production build succeeds.  
- **Open reference samples** — used in integration tests (not live customer data).  

**Not part of this report:** full Docker Compose end-to-end smoke on this host (depends on free ports and Docker permissions). The repository includes `infrastructure/scripts/smoke-e2e.py` and CI job `e2e-open-data-smoke` for that path.

---

## 6. Conclusion for client

Day 1 objectives for **codebase readiness, automated quality gates, and reproducible local verification** are **met**:

- **33** backend automated tests **passed** in the last full local run.  
- **4** frontend tests **passed**.  
- **Production React build** completed successfully.  

This document may be attached as evidence of **Day 1 test execution** together with repository access and, if desired, a link to **GitHub Actions** workflow runs on `main`.

---

*Prepared from commands run against the workspace on 23 March 2026.*
