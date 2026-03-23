# CI/CD

## Continuous integration (`ci.yml`)

- Gradle `test` + `bootJar` per backend service.
- Postgres Testcontainers integration job for account, transaction, openbanking.
- `npm test` + `npm run build` for web-portal.
- Docker image builds (validation only).
- Compose up + Python smoke + **Playwright API** E2E.

## Manual workflows

- **Day 4 / Day 5 verify** — full local test sweep; optional Compose + Trivy.
- **Security scan** — Trivy FS + gateway image.
- **Deploy AKS (tag)** — on `v*` tag push, push images to GHCR; Helm upgrade job gated until Azure secrets exist.

## Production protection

Configure GitHub **Environment** `production` with required reviewers before enabling automated Helm deploy.
