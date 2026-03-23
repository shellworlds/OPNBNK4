# Performance targets (KPIs)

Targets are **directional** for architecture and capacity planning; final SLOs require measurement under representative load.

## Throughput

| Area | Target | Notes |
|------|--------|--------|
| **API aggregate** | **10k TPS** sustained (peak planning) | Mix of read-heavy AIS and payment commands; horizontal scale of stateless services + gateway |
| **Payment initiation** | 1k TPS initial SLO slice | Stronger consistency path; optimize after ledger integration |

## Latency

| Metric | Target | Scope |
|--------|--------|--------|
| **p99 latency** | **< 100 ms** | Authenticated read APIs behind cache (e.g. account summary) at steady state |
| **p99 latency (writes)** | < 250 ms | Payment command acceptance (async settlement may be longer) |
| **Gateway overhead** | < 5 ms p99 added | Excluding backend work |

## Availability and resilience

- **Tier-1 read path**: 99.95% monthly (design for multi-AZ, health checks, circuit breaking).
- **Degraded mode**: Read-only or queued writes under dependency failure, with clear user messaging.

## Efficiency

- Cost per 1M API calls tracked per environment; autoscaling policies tuned against Redis/DB connection pools and Kafka consumer lag.

## Measurement

- Define golden traces and load tests in CI/staging before claiming SLO compliance; correlate with DB query plans and cache hit rates.
