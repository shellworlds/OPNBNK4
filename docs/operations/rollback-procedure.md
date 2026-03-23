# Rollback procedure

## Kubernetes / Helm

```bash
helm history opnbnk4 -n opnbnk4
helm rollback opnbnk4 <revision> -n opnbnk4
kubectl rollout undo deployment/api-gateway -n opnbnk4
kubectl rollout status deployment/api-gateway -n opnbnk4
```

## Docker images (GHCR)

Previous tags remain addressable, e.g. **`ghcr.io/shellworlds/opnbnk4-api-gateway:v0.9.0`**.

To rollback without Helm history:

```bash
kubectl set image deployment/api-gateway api-gateway=ghcr.io/shellworlds/opnbnk4-api-gateway:v0.9.0 -n opnbnk4
```

## Database migrations

- **Forward-only** migrations applied by Flyway: rolling back **code** without reverting schema can break the app.  
- For breaking schema changes, use **expand/contract** pattern or restore DB from backup (see `disaster-recovery.md`).

## Verification after rollback

1. Health endpoints **UP**.  
2. Run Playwright API suite against the environment (or smoke script).  
3. Update `GO_LIVE.md` or incident ticket with rollback timestamp and reason.

## Drill

Quarterly: perform rollback in **staging** and record duration (target RTO per NFR).
