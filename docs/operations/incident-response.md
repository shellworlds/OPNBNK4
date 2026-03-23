# Incident response — post-deployment

## Severity

- **SEV1** — full outage → all hands, exec comms.
- **SEV2** — major degradation → on-call plus lead.
- **SEV3** — minor → backlog if contained.

## Triage

1. Identify (Grafana, users, synthetics).
2. Stabilize (scale, feature off, limit traffic).
3. Communicate (status page per contract).
4. Root cause (logs, traces, deploy correlation).
5. Fix (hotfix or rollback — see rollback-procedure.md).
6. Post-incident note.

## Common issues

| Symptom | Likely cause | Action |
|---------|--------------|--------|
| 503 | Upstream down / CB | kubectl get pods, logs |
| DB errors | Wrong secret / URL | Key Vault, ConfigMap |
| Mobile 401/403 | CORS / JWT audience | Gateway CORS, Keycloak client |
| 429 | Rate limit | Redis limiter tuning |
| Ingress timeout | Large payload | proxy-read-timeout |

## Issues

Use GitHub template **Post-deployment defect**.

## Escalation

Fill L2 and client contacts in runbook Support section.
