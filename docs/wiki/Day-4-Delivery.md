# Day 4 — Delivery

← [Home](Home)

## Scope

- **E2E:** Playwright API tests (`tests/e2e`) wired into **CI** after Compose smoke; web smoke optional (`SKIP_WEB_E2E`).
- **Performance:** JMeter plan `infrastructure/scripts/performance-test.jmx` and report template `docs/performance/report.md` (full SLAs on AKS).
- **Security:** OWASP Dependency-Check Gradle plugin on all Java services; Trivy workflows; `docs/security/penetration-test.md`; secrets rotation and DR docs.
- **Observability:** Prometheus, Grafana (port 3001), Jaeger, MailHog in `docker-compose.yml`; sample Alertmanager config.
- **Deployment:** `infrastructure/k8s/base/`, Helm chart `infrastructure/helm/opnbnk4/`, Terraform stubs; `.github/workflows/deploy-aks.yml` (GHCR on tags).
- **GDPR stubs:** `GET /api/user/export`, `POST /api/user/delete-request` on account-service; notification email to MailHog.

## Client links

- **Handoff:** [docs/submission/CLIENT_HANDOFF_DAY4.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY4.md)
- **DAY4_COMPLETE:** [DAY4_COMPLETE.md](https://github.com/shellworlds/OPNBNK4/blob/main/DAY4_COMPLETE.md)
- **Tag:** `v0.9.0` (release candidate)

## GitHub

- **Dedicated issues:** [docs/github/DAY4_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY4_DEDICATED_ISSUES.md)
- **Actions:** [Day 4 verify (manual)](https://github.com/shellworlds/OPNBNK4/actions/workflows/day4-verify.yml) · [Security scan](https://github.com/shellworlds/OPNBNK4/actions/workflows/security-scan.yml) · [Deploy AKS (tag)](https://github.com/shellworlds/OPNBNK4/actions/workflows/deploy-aks.yml)
