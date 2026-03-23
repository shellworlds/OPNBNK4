# Architecture decision log (ADL)

Concise record of major choices for OPNBNK4. Expand with ADR template as needed.

| Date | Decision | Context | Consequences |
|------|----------|---------|--------------|
| Day 1 | **Kafka** for domain events | Need durable async fan-out (fraud, notifications, analytics) | Operational overhead vs. RabbitMQ; team standardizes on Kafka operators on AKS. |
| Day 2 | **Redis** for gateway rate limiting and cache | Low-latency counters and account read cache | Extra failure domain; TTL and eviction policy must be monitored. |
| Day 3 | **Synchronous fraud HTTP** before transaction complete | Regulatory desire for inline block decision | Adds latency; circuit breaker + fallback policy required. |
| Day 3 | **core-simulator** instead of live core in dev | Safe iteration without mainframe | Contract tests must gate simulator drift. |
| Day 4 | **Prometheus + Grafana** in Compose | Developer visibility before AKS | Not HA; managed Azure Monitor recommended in prod. |
| Day 4 | **Jaeger all-in-one** for traces | Validate OpenTelemetry propagation | Replace with Tempo/Jaeger collector in production. |
| Day 4 | **Playwright** for API E2E | Single toolchain for future web flows | Mobile remains Detox/manual until scheduled. |
| Pending | **Azure Managed Kafka vs. Confluent Cloud** | Enterprise networking and SLAs | Cost vs. managed connector ecosystem. |

## Why not RabbitMQ?

Kafka fits **event log** semantics (replay, multiple consumer groups, retention). RabbitMQ remains excellent for work queues; we may add a dedicated queue service later for per-task delivery guarantees without replay needs.

## GDPR stubs (Day 4)

- `GET /api/user/export` and `POST /api/user/delete-request` on **account-service** demonstrate portability and erasure **request** capture; full anonymization pipelines belong in Day 5–7.
