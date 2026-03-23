# End-to-end tests (Day 4)

## Prerequisites

- **API tests:** full stack running (`docker compose up -d --build` from repo root). Gateway on **8080** with channel header validation (Docker default).
- **Web tests:** web portal on **3000** (optional); set `SKIP_WEB_E2E=1` to run API-only.

## Install

```bash
cd tests/e2e
npm ci
npx playwright install --with-deps chromium
```

## Run

```bash
# API only (recommended for local / CI)
E2E_GATEWAY_URL=http://localhost:8080 npm run test:api

# Include web smoke (portal must be up)
E2E_WEB_URL=http://localhost:3000 npm test

# Skip browser smoke
SKIP_WEB_E2E=1 npm test
```

## Scope

| Area | Coverage |
|------|----------|
| **api/** | Account create/read, transaction create/complete, open banking consent/AIS/PIS/revoke, channel validation 400, GDPR export stub |
| **web/** | Minimal load of portal home (skipped if unreachable) |

**Not automated here (manual / Day 5):** Keycloak user self-registration UI, FIDO2/WebAuthn ceremonies, full OAuth2 authorization-code flow for end users, Detox mobile.

## CI

GitHub Actions runs API E2E after Docker Compose smoke (see root `.github/workflows/ci.yml`).
