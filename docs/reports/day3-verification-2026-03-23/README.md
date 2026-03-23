# Day 3 verification log (local)

**Date:** 2026-03-23  
**Environment:** Gradle 8.x, OpenJDK 21, repository root tests from developer workstation.

## Commands run

```bash
for s in account-service transaction-service openbanking-service api-gateway fraud-detection-service core-simulator notification-service; do
  (cd backend/$s && ./gradlew test --no-daemon -q)
done
```

## Result

All listed services: **tests passed** (exit code 0).

## Notes

- Testcontainers may log Docker cleanup warnings (`permission denied` on container remove) on some hosts; JUnit still reported success.
- Full **Docker Compose** E2E is exercised in GitHub Actions **CI** (`e2e-open-data-smoke`) and optionally **Day 3 verify** with `run_e2e=true`.

## CI

After push to `main`, confirm: https://github.com/shellworlds/OPNBNK4/actions
