# Client handoff — Day 2 (submission-ready)

**Product:** OPNBNK4 — Digital Banking Platform  
**Repository (canonical URL for the client):** https://github.com/shellworlds/OPNBNK4

This file is the **single submission pointer**: share the repository URL above plus this document in `main` (path: `docs/submission/CLIENT_HANDOFF_DAY2.md`).

---

## One-line summary

Day 2 delivers PostgreSQL schemas (Flyway), core account and transaction services with optimistic locking and Kafka/outbox-style events, open banking AIS/PIS and consent APIs, API gateway routing with Redis rate limiting on `/openbanking/**` in Docker, Keycloak in Compose, React portal with optional Keycloak JS and axios Bearer handling, expanded CI (including Postgres Testcontainers job), and documentation (architecture, security, FIDO2 placeholder, DAY2_COMPLETE.md).

---

## Direct links for reviewers

| Area | URL |
|------|-----|
| Repository | https://github.com/shellworlds/OPNBNK4 |
| Latest main tree | https://github.com/shellworlds/OPNBNK4/tree/main |
| GitHub Actions (CI) | https://github.com/shellworlds/OPNBNK4/actions |
| Issues | https://github.com/shellworlds/OPNBNK4/issues |
| Projects (board) | https://github.com/shellworlds/OPNBNK4/projects |
| Project v2 (linked board) | https://github.com/users/shellworlds/projects/6 |
| Wiki (live) | https://github.com/shellworlds/OPNBNK4/wiki |
| Insights / Pulse | https://github.com/shellworlds/OPNBNK4/pulse |
| Releases | https://github.com/shellworlds/OPNBNK4/releases |

---

## Documentation in-repo

| Document | Path on main |
|----------|----------------|
| Day 2 completion notes | DAY2_COMPLETE.md |
| Open banking strategy and API tables | docs/architecture/open-banking-strategy.md |
| Security overview (Keycloak, Key Vault, HSM sim) | docs/architecture/security-overview.md |
| FIDO2 placeholder | docs/security/fido2.md |
| Dedicated issues checklist | docs/github/DAY2_DEDICATED_ISSUES.md |
| Wiki mirror | docs/wiki/ |

Full blob URLs: prefix with `https://github.com/shellworlds/OPNBNK4/blob/main/`.

---

## CI / quality gates

- **On push/PR to main:** workflow `CI` — Gradle test + bootJar per service, job `java-postgres-integration`, npm test + build, Docker builds, Docker Compose + smoke-e2e.py.
- **Manual:** workflow `Day 2 verify (manual)` — full tests plus optional Compose smoke.

Green CI on `main` is the acceptance signal for automated tests.

---

## Wiki sync (maintainer)

Clone https://github.com/shellworlds/OPNBNK4.wiki.git, merge from `docs/wiki/` in this repo, push to update the live wiki.

---

## Submission package

**For the client:** repository URL **https://github.com/shellworlds/OPNBNK4** and this file **docs/submission/CLIENT_HANDOFF_DAY2.md**.

Next steps: see DAY2_COMPLETE.md (Day 3 focus).
