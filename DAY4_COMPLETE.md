# Day 4 complete — E2E, performance prep, security, production readiness (RC)

**Release candidate tag:** `v0.9.0`  
**Repository:** https://github.com/shellworlds/OPNBNK4

## Summary

Day 4 adds **Playwright** API E2E coverage in CI, **Prometheus/Grafana/Jaeger/MailHog** in Docker Compose, **JMeter** plan plus **performance report** template, **OWASP Dependency-Check** on all Java Gradle services, **Trivy** workflows, **Kubernetes** sample manifests and **Helm** skeleton, **Terraform** AKS stubs, **deploy-aks.yml** (GHCR push on tag; Helm job gated), GDPR **export/delete-request** stubs on account-service, **notification-service** email hook to MailHog, and expanded **operations / security / architecture** documentation.

## Performance (SLA status)

| Item | Status |
|------|--------|
| Read API p99 under 100 ms at 10k TPS | **Not proven on laptop Compose** — see `docs/performance/report.md`; run on AKS + managed Postgres |
| Write p99 under 300 ms | **Requires** histogram export from staging load tests |
| 99.99% success at peak | **Requires** scaled cluster and longer soak |

The **JMeter** plan uses **reduced** concurrency for developer hardware; scale thread groups for formal benchmarks.

## Security audit

- **Dependency-Check:** `./gradlew dependencyCheckAnalyze` per service; aggregate script `infrastructure/scripts/run-dependency-check-all.sh`.  
- **Trivy:** workflows `security-scan.yml` and optional step in `day4-verify.yml`.  
- **Pentest notes:** `docs/security/penetration-test.md`.  
- **Secrets:** `docs/operations/secrets-rotation.md` — production must use Key Vault / Vault, not Compose defaults.

## Deployment automation

- **Manifests:** `infrastructure/k8s/base/`  
- **Helm:** `infrastructure/helm/opnbnk4/`  
- **Terraform:** `infrastructure/terraform/` (`aks.tf` commented until Azure is targeted)  
- **CI:** `.github/workflows/deploy-aks.yml` pushes images on **version tags**; **helm-upgrade** job is `if: false` until `AZURE_CREDENTIALS`, `AKS_RG`, `AKS_NAME` exist — enable and attach **production** environment with **required reviewers**.

## Observability

- Prometheus scrapes gateway and core services; Grafana on port **3001**; Jaeger UI on **16686**.  
- Alertmanager sample: `infrastructure/observability/alertmanager-sample.yml`.  
- Full SLO dashboards and Alertmanager wiring remain Day 5–7 polish.

## Known issues / Day 5 plan

- **SonarCloud** workflow is a disabled stub (`sonar-quality.yml`).  
- **OpenAPI aggregation** and **developer-portal** interactive docs: backlog.  
- **Strict JWT** on all routes in Compose: would break unauthenticated smoke unless tokens are wired everywhere.  
- **Coverage 80%+** and **Detox** mobile automation: not claimed for all modules.  
- **Staging AKS / kind:** run `helm upgrade` against a real cluster and attach Playwright to staging URL.

## Estimated remaining effort (Days 5–7)

| Phase | Focus | Rough buffer |
|-------|--------|--------------|
| Day 5 | Sonar remediation, OpenAPI portal, OTLP on all JVMs | 1–2 days |
| Day 6 | Staging hardening, UAT scripts, consent UI | 1 day |
| Day 7 | Go-live checklist, DR drill, client training | 1 day |

## Client submission

- **Handoff:** `docs/submission/CLIENT_HANDOFF_DAY4.md`  
- **Verification table:** `docs/submission/VERIFICATION_RESULTS_DAY4.md`  
- **Quick index:** `SUBMISSION_DAY4.md`
