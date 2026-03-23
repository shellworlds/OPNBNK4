# Final performance tuning — Day 5

## Checklist (run in staging / AKS)

- [ ] `EXPLAIN ANALYZE` on hot queries (account list, transaction list by account) — Flyway indexes on Day 2–3.
- [ ] Redis cache hit rate for account reads — target **80%+** under steady load (Micrometer cache metrics when enabled).
- [ ] Kafka consumer lag **under 100** messages per group under nominal TPS.
- [ ] Hikari pool sizes: ensure `pool size * replicas < Postgres max_connections - admin reserve`.

## Load test (1500 users × 10 minutes)

Run JMeter (or k6) against **staging** — not the default laptop Compose profile. Record p99 and error % in `deliverables/performance-report/` and `docs/performance/report.md`.

**Note:** Claiming specific p99/TPS numbers requires that run; CI does not execute full load tests by default.
