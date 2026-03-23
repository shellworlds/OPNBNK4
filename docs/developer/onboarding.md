# Developer onboarding

## Clone and prerequisites

- JDK **21**, Docker / Docker Compose, Node **20** (web + E2E).

## Backend

```bash
cd backend/account-service && ./gradlew test
```

Repeat for other services or rely on CI matrix.

## Full stack

```bash
docker compose up --build
```

Gateway: http://localhost:8080 — Keycloak: 8180 — Web portal: 3000.

## E2E

```bash
cd tests/e2e && npm ci && npx playwright install
E2E_GATEWAY_URL=http://localhost:8080 npm run test:api
```

## IDE

Import Gradle projects separately or use multi-root workspace. Lombok annotation processing enabled.
