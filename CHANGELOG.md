# Changelog

All notable changes to **OPNBNK4** (Digital Banking Platform) are documented here.

## [1.0.0] — 2026-03-23

### Added

- **UAT:** `docs/uat/uat-script.md`, `uat-results.md`, `sign-off-placeholder.md`.
- **Transaction status `UNDER_REVIEW`** when fraud verdict is **REVIEW**; Flyway `V3__transaction_under_review_status.sql`; event `TRANSACTION_FRAUD_REVIEW`.
- **PIS:** `OpenbankingDownstreamService` returns downstream completion **status** (`COMPLETED` or `UNDER_REVIEW`).
- **E2E:** Playwright test for high-value EUR payment held for review.
- **UI:** Loading spinner, `formatApiError` in `api.js`, mock login submit disabled state, `UNDER_REVIEW` styling in account transactions.
- **Docs:** `docs/architecture/final-architecture.md`, `api-reference.md`; operations (deployment, monitoring, scaling, troubleshooting, pre-deploy, smoke-go-live); developer (onboarding, coding-standards, ci-cd); user (web, mobile, faq); compliance (gdpr, psd2, audit-log); `docs/performance/final-tuning.md`; DR drill section in `disaster-recovery.md`.
- **Deliverables:** `deliverables/` bundle (README, k8s/terraform copies, UAT, performance, security, architecture, demo instructions).
- **Reports:** `GO_LIVE.md`, `PROJECT_COMPLETION_REPORT.md`, `DAY5_COMPLETE.md`, `docs/submission/CLIENT_HANDOFF_DAY5.md`, `docs/github/DAY5_DEDICATED_ISSUES.md`.
- **CI:** `.github/workflows/day5-verify.yml` (manual).
- **Wiki:** Day-5-Delivery; Releases v1.0.0.

### Changed

- **Helm / sample K8s** image tags default to **v1.0.0**.
- **`.gitignore`:** `deliverables/demo/*.mp4` (and `.mov`).

## [0.9.0-day4] — 2026-03-23

### Added

- **E2E:** Playwright suite under `tests/e2e` (API + optional web smoke); **CI** runs `npm run test:api` after Compose smoke; `package-lock.json` for reproducible installs.
- **Observability:** Prometheus, Grafana (port 3001), Jaeger, MailHog in `docker-compose.yml`; `micrometer-registry-prometheus` exposure documented in runbook; sample `alertmanager-sample.yml`.
- **Performance:** `docs/performance/report.md`; JMeter results directory `infrastructure/scripts/results/` (gitkept).
- **Security:** OWASP Dependency-Check Gradle plugin on all Java services; `infrastructure/scripts/run-dependency-check-all.sh`; `security-scan.yml` (Trivy FS + image); `docs/security/penetration-test.md`.
- **Operations:** `docs/operations/secrets-rotation.md`, `disaster-recovery.md`; runbook sections for observability, E2E, JMeter, K8s.
- **Architecture:** `docs/architecture/decision-log.md`; HLD Day 4 sequence diagrams.
- **GDPR stubs:** `UserDataController` on account-service (`/api/user/export`, `/api/user/delete-request`).
- **Notifications:** Mail send path in `NotificationListeners` when `notification.email.enabled=true`; `application-docker.yml` mail + flag mapping.
- **Deployment:** `infrastructure/k8s/base/` manifests, `infrastructure/helm/opnbnk4/`, Terraform `variables.tf` + commented `aks.tf`, `.github/workflows/deploy-aks.yml` (GHCR push on tags; Helm job disabled until Azure).
- **CI:** `day4-verify.yml`, `sonar-quality.yml` stub; wiki **Day-4-Delivery**; `DAY4_COMPLETE.md`, `SUBMISSION_DAY4.md`, `CLIENT_HANDOFF_DAY4.md`, `VERIFICATION_RESULTS_DAY4.md`, `docs/github/DAY4_DEDICATED_ISSUES.md`.

### Notes

- Formal **p99 / 99.99%** SLAs require staging cluster benchmarks (see performance report). Enable **production** environment + Helm job when Azure credentials exist. **Sonar** job remains off until tokens are set.

## [0.3.0-day3] — 2026-03-23

### Added

- **core-simulator**, **fraud-detection-service**, **notification-service** with Dockerfiles; Compose wiring.
- Account-service: **CoreSimulatorClient** (Resilience4j), Redis **@Cacheable** for `getAccount`, extended **AccountResponse**.
- Transaction-service: synchronous **fraud evaluation** before complete; **X-Device-Id** header.
- API gateway: **circuit breaker** fallbacks, optional **channel header** validation (Docker).
- Flyway **V2** indexes on transactions and consents; fraud **ML stub** REST; smoke script channel headers.
- Docs: `DAY3_COMPLETE.md`, `docs/submission/CLIENT_HANDOFF_DAY3.md`, `docs/github/DAY3_DEDICATED_ISSUES.md`, `docs/security/fraud-detection.md`, `docs/operations/runbook.md`; wiki source **Day-3-Delivery**.
- CI: Gradle matrix includes fraud, core-simulator, notification; Docker builds for those images; manual **Day 3 verify** workflow.
- Dependabot: Gradle entries for new services; npm for **frontend/mobile-app**.

### Fixed

- **Docker / Compose:** Images for `account-service`, `transaction-service`, `fraud-detection-service`, and `notification-service` now use **`docker build` context `./backend`** so Gradle composite `includeBuild("../shared-libs/events")` resolves inside the image. CI `docker-validate` updated accordingly. Added `backend/.dockerignore`.

### Notes

- Create GitHub **Release** tag `v0.3.0-day3` when publishing to clients. WebAuthn, Vault, full observability, and GHCR remain Day 4+.

## [0.2.0-day2] — 2026-03-23

### Added

- Flyway migrations (accounts, transactions, open banking), optimistic locking on balances, Kafka/outbox patterns, open banking AIS/PIS + consent filter.
- Docker Compose: Kafka, Kafka UI, Redis, Keycloak realm import; gateway Redis rate limit on `/openbanking/**` (docker profile).
- Frontend: Keycloak JS, axios Bearer interceptor, account detail + transactions, nginx production image.
- CI: `java-postgres-integration` job; manual **Day 2 verify** workflow.
- Docs: `DAY2_COMPLETE.md`, `docs/submission/CLIENT_HANDOFF_DAY2.md`, `docs/github/DAY2_DEDICATED_ISSUES.md`, FIDO2 placeholder; wiki sources for Day 2.
- GitHub: issue templates for wiki sync, Day 3 JWT, Day 3 OpenAPI.

### Notes

- Core APIs in Compose remain open (no `KEYCLOAK_ISSUER_URI`) so smoke tests pass without tokens; harden in Day 3.

## [0.1.0-day1] — 2026-03-23

### Added

- Monorepo layout: `backend/` (account, transaction, openbanking, api-gateway), `frontend/web-portal`, `infrastructure/`, `docs/`.
- Architecture documentation under `docs/architecture/`.
- JPA-backed REST APIs, Spring Cloud Gateway, React portal (routing, mock login, dashboard).
- Docker Compose (Postgres, Redis, Kafka, services); Dockerfiles per component.
- GitHub Actions: CI (Gradle, npm, Docker), E2E smoke job, **Day 1 verify (manual)** workflow.
- Automated tests: JUnit (incl. Testcontainers PostgreSQL + public IBAN-style fixtures), WireMock gateway IT, React tests.
- Scripts: `infrastructure/scripts/smoke-e2e.py`, Postgres init SQL.
- Client report: `docs/day1-client-test-report.md`; submission summary: `docs/day1-complete-report.md`; raw verification logs: `docs/reports/day1-verification-2026-03-23/`; verification: `docs/verification.md`.
- Wiki mirror: `docs/wiki/`; README links to GitHub Project, Issues, Actions, Insights.

### Notes

- Mobile app folder remains a stub for future React Native work.
- Production OAuth2, Kafka business logic, and full cloud deployment are out of scope for this milestone.
