# Day 3 — verification results and outputs

**Run date:** 2026-03-23 (UTC)

This document backs the **client submission** ([CLIENT_HANDOFF_DAY3.md](./CLIENT_HANDOFF_DAY3.md)) with concrete command outcomes.

---

## 1. Backend — Gradle `test` + `bootJar`

**Host:** OpenJDK 21 (Temurin), Gradle wrapper per service.

| Service | Result |
|---------|--------|
| account-service | **PASS** |
| transaction-service | **PASS** |
| openbanking-service | **PASS** |
| api-gateway | **PASS** |
| fraud-detection-service | **PASS** |
| core-simulator | **PASS** |
| notification-service | **PASS** |

**Note:** Testcontainers may log Docker cleanup warnings on some Linux hosts; JUnit exits **0**.

---

## 2. Frontend — web portal

**Node:** v20.x, `frontend/web-portal`

| Step | Result |
|------|--------|
| `npm ci` | **PASS** |
| `npm test -- --watchAll=false` | **PASS** (4 suites, 5 tests) |
| `npm run build` | **PASS** |

---

## 3. Docker — composite `shared-events` build

Services using `includeBuild("../shared-libs/events")` use **`docker build` context `./backend`** (see `docker-compose.yml` and each service `Dockerfile`).

**Verified:**

```bash
docker build -f backend/account-service/Dockerfile -t dbp-account:local backend
```

**Result:** **PASS**.

**Compose build:** All application images (account, transaction, fraud, notification, openbanking, gateway, core-simulator, web-portal, mobile-web) **built successfully** in verification.

---

## 4. End-to-end smoke

```bash
docker compose up -d --build
python3 infrastructure/scripts/smoke-e2e.py --gateway http://localhost:8080
docker compose down -v --remove-orphans
```

**CI:** GitHub Actions job `e2e-open-data-smoke` runs this after `docker-validate`.

**Local:** Requires Docker to start/stop containers without permission errors on stale containers.

---

## 5. CI

Confirm after push: https://github.com/shellworlds/OPNBNK4/actions

---

## Summary

| Gate | Status |
|------|--------|
| Backend tests + bootJar | **PASS** (local) |
| Web portal test + build | **PASS** (local) |
| Docker image (composite) | **PASS** (fixed + verified) |
| Compose smoke | **CI** + local when Docker is healthy |
