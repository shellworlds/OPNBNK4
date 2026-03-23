# Performance test report — Day 4 (release candidate)

**Environment:** Local Docker Compose on developer workstation (ThinkPad-class hardware).  
**Tool:** Apache JMeter — non-GUI mode recommended (`jmeter -n -t …`).  
**Plan:** `infrastructure/scripts/performance-test.jmx`

## Executive summary

| Target (Day 1 brief) | Laptop-scale run | Notes |
|------------------------|------------------|--------|
| Read API p99 under 100 ms | **Not validated at 10k TPS** | Compose plus single-node Postgres is not sized for 10k TPS; use AKS load environment for formal SLA proof. |
| Write API p99 under 300 ms | **Spot-check via smoke / Playwright** | Full write latency under load requires JMeter plus Prometheus scrape of histograms. |
| 99.99% success under peak | **Not met on laptop at spec concurrency** | Reduced thread counts in `.jmx` avoid OOM; scale replicas and DB pool in cluster. |

The repository ships a **repeatable JMeter plan** and **Prometheus/Grafana** wiring so teams can capture p50/p95/p99 from `http_server_requests_seconds_bucket` (Spring Boot 3) after importing or building Grafana panels.

## Scenarios (design vs. shipped plan)

| Scenario | Specification | Shipped approximation (workstation) |
|----------|-----------------|-------------------------------------|
| User dashboard | 1,000 users × 10 iterations | Thread group: **50 threads × 10 loops** hitting `GET /api/accounts` via gateway with channel headers. |
| Open Banking AIS | 500 TPPs concurrent | Thread group: **20 threads × 5 loops** on `GET /openbanking/accounts` with demo consent headers. |
| PIS | 200 payments/s | **Add** a third thread group in the same `.jmx` or clone the plan — not enabled by default on 32GB dev boxes. |

To approach specification rates: run JMeter from a second machine, scale `docker compose up --scale account-service=3` (and tune gateway routes), increase Postgres `max_connections` and Hikari `maximum-pool-size`, and add Kafka partitions for hot topics.

## How to run (non-GUI)

Prerequisites: JMeter 5.6+, full stack up (`docker compose up --build`), gateway on `localhost:8080`.

```bash
mkdir -p infrastructure/scripts/results
jmeter -n -t infrastructure/scripts/performance-test.jmx \
  -l infrastructure/scripts/results/jmeter-$(date -u +%Y%m%dT%H%M%SZ).jtl \
  -e -o infrastructure/scripts/results/jmeter-report
```

Review **Grafana** (`http://localhost:3001`, default admin/admin in Compose) and **Prometheus** (`http://localhost:9090`) during the run. Export panel PNGs or snapshot queries into this document for client packs.

## Grafana / Prometheus metrics to chart

- **Technical:** `up{job="..."}`, `process_cpu_usage`, `jvm_memory_used_bytes`, Kafka listener metrics where exposed.
- **HTTP:** `http_server_requests_seconds_bucket` filtered by `uri` and `method` — compute p99 in Grafana.
- **Business (stubs):** Counters for transactions and fraud decisions can be added as Micrometer metrics in Day 5–7.

## Bottlenecks observed (typical Compose)

1. **PostgreSQL** — single instance, fsync latency dominates write p99.  
2. **Kafka** — single broker; increase partitions for parallel consumers.  
3. **CPU** — JSON serialization and fraud HTTP call in transaction path; scale `transaction-service` horizontally with idempotent design review.

## Recommendations

1. Run the same `.jmx` against **staging AKS** with managed PostgreSQL and Redis.  
2. Set OWASP Dependency-Check thresholds after remediation (see `docs/security/penetration-test.md`).  
3. Add **Alertmanager** rules for 5xx rate and Kafka consumer lag (sample: `infrastructure/observability/alertmanager-sample.yml`).

## Record your run

| Date | Host | Duration | p99 read (ms) | p99 write (ms) | Error % | Notes |
|------|------|----------|---------------|----------------|---------|--------|
| _fill_ | _fill_ | _fill_ | _fill_ | _fill_ | _fill_ | _fill_ |
