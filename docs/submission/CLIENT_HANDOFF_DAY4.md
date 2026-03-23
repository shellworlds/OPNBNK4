# Client handoff — Day 4 (submission-ready)

**Product:** OPNBNK4 — Digital Banking Platform  
**Repository (canonical URL for the client):** https://github.com/shellworlds/OPNBNK4

**Submission pointer:** share the repository URL above plus this document on `main` at **`docs/submission/CLIENT_HANDOFF_DAY4.md`**.

**Blob URL (direct link for reviewers):**  
https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY4.md

**Quick index:** [SUBMISSION_DAY4.md](https://github.com/shellworlds/OPNBNK4/blob/main/SUBMISSION_DAY4.md) lists this file and verification outputs.

---

## Verification results

See **[VERIFICATION_RESULTS_DAY4.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/VERIFICATION_RESULTS_DAY4.md)** for the checklist. CI on `main` runs Gradle, Postgres IT, npm, Docker builds, Python smoke, and **Playwright API E2E** against Compose.

---

## One-line summary

Day 4 delivers **Playwright API E2E** in CI, **observability stack** (Prometheus, Grafana, Jaeger) and **MailHog** in Compose, **JMeter** performance assets and **performance report**, **security scanning** (OWASP Dependency-Check per service, Trivy workflows, penetration-test notes), **Kubernetes/Helm/Terraform** scaffolding, **GHCR image publish on tags** via `deploy-aks.yml`, GDPR **export/delete-request** stubs, **notification email** to MailHog, and **Day 4 documentation** (runbook, secrets rotation, DR, decision log, user guide draft).

---

## Direct links for reviewers

| Area | URL |
|------|-----|
| **Repository** | https://github.com/shellworlds/OPNBNK4 |
| **Actions** | https://github.com/shellworlds/OPNBNK4/actions |
| **Issues** | https://github.com/shellworlds/OPNBNK4/issues |
| **Projects** | https://github.com/shellworlds/OPNBNK4/projects |
| **Wiki (live)** | https://github.com/shellworlds/OPNBNK4/wiki |
| **Insights / Pulse** | https://github.com/shellworlds/OPNBNK4/pulse |

---

## Documentation in-repo (Day 4)

| Document | Path on `main` |
|----------|----------------|
| Day 4 engineering notes | [DAY4_COMPLETE.md](https://github.com/shellworlds/OPNBNK4/blob/main/DAY4_COMPLETE.md) |
| Performance report | [docs/performance/report.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/performance/report.md) |
| Penetration test (simulated) | [docs/security/penetration-test.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/security/penetration-test.md) |
| Secrets rotation | [docs/operations/secrets-rotation.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/operations/secrets-rotation.md) |
| Disaster recovery | [docs/operations/disaster-recovery.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/operations/disaster-recovery.md) |
| Architecture decision log | [docs/architecture/decision-log.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/architecture/decision-log.md) |
| User guide (draft) | [docs/user/user-guide.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/user/user-guide.md) |
| E2E README | [tests/e2e/README.md](https://github.com/shellworlds/OPNBNK4/blob/main/tests/e2e/README.md) |
| Dedicated GitHub issues | [docs/github/DAY4_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY4_DEDICATED_ISSUES.md) |
| Wiki mirror | [docs/wiki/](https://github.com/shellworlds/OPNBNK4/tree/main/docs/wiki) |

---

## GitHub wrap-up (maintainer)

1. **Release:** Tag **`v0.9.0`** (release candidate). Optional GitHub Release with notes from `CHANGELOG.md`.  
2. **Environments:** Configure **production** environment with **required reviewers** before enabling the `helm-upgrade` job in `deploy-aks.yml`.  
3. **Issues:** Open items from **[DAY4_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY4_DEDICATED_ISSUES.md)** and attach to the project board.  
4. **Wiki:** Sync `docs/wiki/` to `OPNBNK4.wiki.git` (includes **Day-4-Delivery**).

---

## Submission package (for the client)

| Deliverable | Value |
|-------------|--------|
| **Repository** | https://github.com/shellworlds/OPNBNK4 |
| **Submission document** | https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY4.md |
| **Verification results** | https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/VERIFICATION_RESULTS_DAY4.md |
| **Quick index (root)** | https://github.com/shellworlds/OPNBNK4/blob/main/SUBMISSION_DAY4.md |
