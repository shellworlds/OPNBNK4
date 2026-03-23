# Verification results — Day 4

**Date:** 2026-03-23 (release candidate)  
**Repo:** https://github.com/shellworlds/OPNBNK4

## Automated (expected on green `main`)

| Gate | Workflow / command | Result |
|------|-------------------|--------|
| Gradle unit tests | `ci.yml` matrix (7 services) | Run on push/PR |
| Postgres integration | `ci.yml` `java-postgres-integration` | Run on push/PR |
| Web portal | `ci.yml` `react-web-portal` | npm test + build |
| Docker images | `ci.yml` `docker-validate` | Build all service images |
| Compose smoke | `ci.yml` `e2e-open-data-smoke` | `smoke-e2e.py` |
| Playwright API | `ci.yml` (after smoke) | `tests/e2e` project `api` |

## Local / maintainer

| Check | Command / note | Result |
|-------|----------------|--------|
| OWASP Dependency-Check | `infrastructure/scripts/run-dependency-check-all.sh` | Reports in each service `build/reports/` |
| JMeter | `jmeter -n -t infrastructure/scripts/performance-test.jmx` | Requires JMeter CLI installed |
| Trivy | `.github/workflows/security-scan.yml` (manual) | SARIF optional upload |

## Performance SLAs

Formal p99 and error-rate numbers under target load are **not** recorded here for laptop-only runs. Use `docs/performance/report.md` after AKS staging tests.

## Security

See `docs/security/penetration-test.md` for simulated findings and mitigations.
