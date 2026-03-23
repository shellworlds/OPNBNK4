# Changelog

All notable changes to **OPNBNK4** (Digital Banking Platform) are documented here.

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
