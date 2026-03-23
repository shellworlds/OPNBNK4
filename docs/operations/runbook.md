# Operations runbook (local Docker Compose)

## Start the stack

From the repository root:

```bash
docker compose up --build
```

Wait until **api-gateway** listens on **8080**, **Keycloak** on **8180**, and **web-portal** on **3000** (if built).

## Gateway headers (Day 3)

When `GATEWAY_CHANNEL_VALIDATION_ENABLED=true`, every routed API call should include:

- `X-Client-Id` — stable client identifier (e.g. `web-portal`, `mobile-expo`).
- `X-Client-Channel` — `web`, `mobile`, or `automation` for smoke tests.

Optional:

- `X-Device-Id` — forwarded to fraud evaluation on transaction completion.

The smoke script `infrastructure/scripts/smoke-e2e.py` sets defaults for automation.

## Login (Keycloak)

1. Open the web portal (e.g. `http://localhost:3000`).
2. Sign in with a user from the imported realm (see Keycloak import under `infrastructure/keycloak/`).

## Open banking / mock TPP

1. Create accounts and consents (smoke script does this), or use **Postman** with the same JSON bodies.
2. Call AIS with demo headers, for example `X-Demo-Tpp-Id` and `X-Demo-Customer-Id`, as used in `smoke-e2e.py`.

## Health checks

- Gateway: `GET http://localhost:8080/actuator/health` (no channel headers required; `/actuator` is excluded from channel validation).

## Stop

```bash
docker compose down
```

## Observability (Day 4)

| Tool | URL (Compose) | Purpose |
|------|----------------|---------|
| Prometheus | http://localhost:9090 | Scrape `/actuator/prometheus` from gateway and services |
| Grafana | http://localhost:3001 | Dashboards (admin / admin) |
| Jaeger UI | http://localhost:16686 | Trace visualization (OTLP 4317/4318 on `jaeger`) |
| MailHog | http://localhost:8025 | Capture SMTP from `notification-service` |

## E2E tests (Playwright)

From repo root with the stack running:

```bash
cd tests/e2e
npm ci
npx playwright install
E2E_GATEWAY_URL=http://localhost:8080 npm run test:api
```

Web smoke (optional): omit `SKIP_WEB_E2E` and ensure `web-portal` is up.

## Performance (JMeter)

See `docs/performance/report.md`. Non-GUI example:

```bash
mkdir -p infrastructure/scripts/results
jmeter -n -t infrastructure/scripts/performance-test.jmx -l infrastructure/scripts/results/run.jtl
```

## Security scans

- **OWASP Dependency-Check:** `infrastructure/scripts/run-dependency-check-all.sh` (set `NVD_API_KEY` for reliable NVD access).  
- **Trivy:** GitHub Actions workflow **Security scan (manual)** or `trivy fs .` locally.

## Kubernetes / Helm (staging)

Apply manifests under `infrastructure/k8s/base/` or `helm upgrade` using `infrastructure/helm/opnbnk4/`. Production images are pushed by **Deploy AKS (tag)** when a version tag is created.

## GDPR stubs

- `GET /api/user/export?customerId=` — account-service JSON export.  
- `POST /api/user/delete-request?customerId=` — records erasure intent (soft delete pipeline TBD).

## Fraud review (Day 5)

- Completing a transaction when the fraud service returns **REVIEW** (e.g. EUR equivalent above the review threshold) sets status **`UNDER_REVIEW`** and emits `TRANSACTION_FRAUD_REVIEW`.  
- **BLOCK** still returns HTTP **403**.  
- UAT script: `docs/uat/uat-script.md`.
