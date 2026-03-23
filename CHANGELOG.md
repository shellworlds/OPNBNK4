# Changelog

All notable changes to **OPNBNK4** (Digital Banking Platform) are documented here.

## [0.1.0-day1] — 2026-03-23

### Added

- Monorepo layout: `backend/` (account, transaction, openbanking, api-gateway), `frontend/web-portal`, `infrastructure/`, `docs/`.
- Architecture documentation under `docs/architecture/`.
- JPA-backed REST APIs, Spring Cloud Gateway, React portal (routing, mock login, dashboard).
- Docker Compose (Postgres, Redis, Kafka, services); Dockerfiles per component.
- GitHub Actions: CI (Gradle, npm, Docker), E2E smoke job, **Day 1 verify (manual)** workflow.
- Automated tests: JUnit (incl. Testcontainers PostgreSQL + public IBAN-style fixtures), WireMock gateway IT, React tests.
- Scripts: `infrastructure/scripts/smoke-e2e.py`, Postgres init SQL.
- Client report: `docs/day1-client-test-report.md`; verification: `docs/verification.md`.
- Wiki mirror: `docs/wiki/`; README links to GitHub Project, Issues, Actions, Insights.

### Notes

- Mobile app folder remains a stub for future React Native work.
- Production OAuth2, Kafka business logic, and full cloud deployment are out of scope for this milestone.
