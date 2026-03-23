# Monitoring guide

## Compose (developer)

- **Prometheus:** http://localhost:9090 — targets for gateway, services with `/actuator/prometheus`.
- **Grafana:** http://localhost:3001 (default admin/admin in Compose — **change in any shared env**).
- **Jaeger:** http://localhost:16686 — trace UI when OTLP is enabled on JVMs.

## Useful Prometheus queries

- HTTP latency: `histogram_quantile(0.99, sum(rate(http_server_requests_seconds_bucket[5m])) by (le, uri))`
- Up: `up{job=~".+"}`

## Alerts

- Start from `infrastructure/observability/alertmanager-sample.yml` and Prometheus `rule_files` (add in prod).
- Alert on: 5xx rate, pod restarts, Kafka consumer lag (exporter required).

## Logs

- **Azure:** Log Analytics workspace + container insights for AKS.
- **Compose:** `docker compose logs -f api-gateway` (structured JSON recommended in future).
