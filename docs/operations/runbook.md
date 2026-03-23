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
