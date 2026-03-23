# Post-deployment validation (staging / production)

Use this checklist after every release. Replace hostnames and namespaces with your environment values.

**Default manifest namespace in this repo:** `opnbnk4` (some runbooks refer to `digital-banking` — align with your cluster).

## 1. Kubernetes health

```bash
kubectl get pods -n opnbnk4 -o wide
kubectl get svc,ingress -n opnbnk4
kubectl describe pod -n opnbnk4 -l app=api-gateway
```

**Pass criteria:** all workload pods **Running**; readiness probes green; no crash loops.

## 2. Ingress and TLS

```bash
curl -sI https://YOUR_HOST/actuator/health
openssl s_client -connect YOUR_HOST:443 -servername YOUR_HOST </dev/null 2>/dev/null | openssl x509 -noout -dates
```

**Pass criteria:** HTTP 200 on health; certificate dates valid; chain trusted.

## 3. Application smoke

| Check | Command / action |
|-------|------------------|
| Gateway health | GET https://YOUR_HOST/actuator/health |
| Account API (with headers) | GET /api/accounts with X-Client-Id and X-Client-Channel |
| Web portal | Browser load homepage over HTTPS |
| Open banking | Smoke script or Postman collection |

## 4. Logs (last 24h)

- AKS: Log Analytics queries for errors and HTTP 5xx.
- kubectl: kubectl logs deploy/api-gateway -n opnbnk4 --since=24h

**Pass criteria:** no unexpected 5xx bursts.

## 5. Grafana / Prometheus

Review: request rate, error rate, p99 latency, pool usage, Kafka lag (if exporter), Redis hits, CPU/memory.

## 6. Rollback image availability

Confirm GHCR still has previous tags (e.g. v0.9.0).

## Sign-off

| Role | Name | Date | Notes |
|------|------|------|--------|
| Platform | | | |
