# Troubleshooting

| Symptom | Checks |
|---------|--------|
| 502/503 from gateway | `kubectl get pods`, service endpoints, circuit breaker logs |
| 400 missing channel headers | Send `X-Client-Id` and `X-Client-Channel` when validation enabled |
| 403 on transaction complete | Fraud **BLOCK** or policy; check fraud service response |
| Transaction stuck **UNDER_REVIEW** | Expected for EUR-equivalent > €5k review threshold; ops workflow TBD |
| Redis errors | `redis-cli ping`, network policy, credentials |
| Kafka lag | Kafka UI (8090 in Compose), consumer group offsets |
| Keycloak login loops | Realm URL, client redirect URIs, CORS |

## Support escalation

1. On-call engineer checks Grafana + logs.  
2. Roll back deployment if SLO burn.  
3. Post-incident: update this section with new runbook lines.
