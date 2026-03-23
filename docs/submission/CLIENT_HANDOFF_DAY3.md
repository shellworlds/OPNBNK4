# Client handoff — Day 3 (submission-ready)

**Product:** OPNBNK4 — Digital Banking Platform  
**Repository (canonical URL for the client):** https://github.com/shellworlds/OPNBNK4

**Submission pointer:** share the repository URL above plus this document on `main` at **`docs/submission/CLIENT_HANDOFF_DAY3.md`**.

**Blob URL (direct link for reviewers):**  
https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY3.md

**Quick index:** [SUBMISSION_DAY3.md](https://github.com/shellworlds/OPNBNK4/blob/main/SUBMISSION_DAY3.md) (root) lists this file and verification outputs.

---

## Verification results (final)

Automated and manual checks are recorded in **[VERIFICATION_RESULTS_DAY3.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/VERIFICATION_RESULTS_DAY3.md)**:

- All seven backend services: **Gradle `test` + `bootJar` — PASS**
- Web portal: **npm test + build — PASS**
- Docker: **composite `shared-libs/events` image build — PASS** (Dockerfiles use `context: ./backend`)
- E2E smoke: **runs in CI**; locally requires Docker permission to start/stop the full stack

---

## One-line summary

Day 3 adds omni-channel readiness (Expo mobile app + static `mobile-web` in Compose from prior increment), **core banking simulator** integration with **Resilience4j** in account-service, **Redis-backed account read cache**, synchronous **fraud evaluation** before transaction completion, **fraud-detection** and **notification** Kafka services, API gateway **channel header validation** (Docker) and **circuit-breaker fallbacks**, Flyway **performance indexes**, expanded **CI** coverage for new Gradle services and Docker images, **Dependabot** paths for new backends (and optional mobile npm), updated **wiki** sources, and **dedicated GitHub issues** for closure plus Day 4 backlog (`docs/github/DAY3_DEDICATED_ISSUES.md`).

---

## Direct links for reviewers

| Area | URL |
|------|-----|
| **Repository** | https://github.com/shellworlds/OPNBNK4 |
| **Latest `main` tree** | https://github.com/shellworlds/OPNBNK4/tree/main |
| **GitHub Actions (CI)** | https://github.com/shellworlds/OPNBNK4/actions |
| **Issues** | https://github.com/shellworlds/OPNBNK4/issues |
| **Projects (board)** | https://github.com/shellworlds/OPNBNK4/projects |
| **Project v2 (linked board)** | https://github.com/users/shellworlds/projects/6 |
| **Wiki (live)** | https://github.com/shellworlds/OPNBNK4/wiki |
| **Insights / Pulse** | https://github.com/shellworlds/OPNBNK4/pulse |
| **Releases** | https://github.com/shellworlds/OPNBNK4/releases |
| **Dependency graph / Dependabot** | https://github.com/shellworlds/OPNBNK4/network/dependencies |

---

## Documentation in-repo (Day 3)

| Document | Path on `main` |
|----------|----------------|
| Day 3 engineering notes | [DAY3_COMPLETE.md](https://github.com/shellworlds/OPNBNK4/blob/main/DAY3_COMPLETE.md) |
| Fraud approach | [docs/security/fraud-detection.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/security/fraud-detection.md) |
| Operations runbook | [docs/operations/runbook.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/operations/runbook.md) |
| HLD (Day 3 section) | [docs/architecture/high-level-design.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/architecture/high-level-design.md) |
| Dedicated issues (create on GitHub) | [docs/github/DAY3_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY3_DEDICATED_ISSUES.md) |
| Day 2 handoff (previous milestone) | [docs/submission/CLIENT_HANDOFF_DAY2.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY2.md) |
| Wiki mirror (sync to live wiki) | [docs/wiki/](https://github.com/shellworlds/OPNBNK4/tree/main/docs/wiki) |
| Local verification log | [docs/reports/day3-verification-2026-03-23/README.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/reports/day3-verification-2026-03-23/README.md) |
| **Verification results (tables + outputs)** | [docs/submission/VERIFICATION_RESULTS_DAY3.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/VERIFICATION_RESULTS_DAY3.md) |

---

## CI / quality gates

- **On push/PR to `main`:** workflow **CI** (`ci.yml`) — Gradle `test` + `bootJar` for all backend services (including `fraud-detection-service`, `core-simulator`, `notification-service`), Postgres Testcontainers job, npm web-portal, Docker image builds (including new services), Compose **E2E** + `smoke-e2e.py` (channel headers aligned with gateway validation).
- **Manual:** workflow **Day 3 verify (manual)** (`day3-verify.yml`) — full Gradle tests, Postgres IT, npm, optional Compose smoke.

Green **CI** on `main` is the primary automated acceptance signal.

---

## Wiki sync (maintainer)

1. Clone `https://github.com/shellworlds/OPNBNK4.wiki.git`
2. Merge or copy from `docs/wiki/` in this repository (includes **Day-3-Delivery** and hub updates).
3. `git push` to publish **https://github.com/shellworlds/OPNBNK4/wiki**

Use issue template **Day 3 Wiki sync** or an item from [DAY3_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY3_DEDICATED_ISSUES.md).

---

## GitHub wrap-up (maintainer checklist)

After merge to `main`:

1. **Release:** Create **[v0.3.0-day3](https://github.com/shellworlds/OPNBNK4/releases/new)** (tag `v0.3.0-day3`) with notes from `CHANGELOG.md` § 0.3.0-day3.
2. **Milestones:** Use **Day 3** for closure items; **Day 4** for new backlog.
3. **Issues:** Open issues from **[docs/github/DAY3_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY3_DEDICATED_ISSUES.md)** and attach them to **[Project #6](https://github.com/users/shellworlds/projects/6)**.
4. **Projects:** Move Day 3 delivery items to **Done**; backlog to **Todo** / **In Progress**.

---

## Submission package (for the client)

| Deliverable | Value |
|-------------|--------|
| **Repository** | https://github.com/shellworlds/OPNBNK4 |
| **Submission document** | https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY3.md |
| **Verification results** | https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/VERIFICATION_RESULTS_DAY3.md |
| **Quick index (root)** | https://github.com/shellworlds/OPNBNK4/blob/main/SUBMISSION_DAY3.md |

**Optional:** Email or ticket body can be: *“Day 3: https://github.com/shellworlds/OPNBNK4 — handoff https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY3.md — verification https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/VERIFICATION_RESULTS_DAY3.md”*

---

## Known follow-ups (Day 4+)

See [DAY3_COMPLETE.md](https://github.com/shellworlds/OPNBNK4/blob/main/DAY3_COMPLETE.md): WebAuthn/FIDO2, Vault/Azure secrets, aggregated OpenAPI `/docs`, developer portal, JMeter, Prometheus/Grafana/ELK, GHCR publish, and further hardening.
