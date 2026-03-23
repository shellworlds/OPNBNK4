# Scaling guide

## Horizontal pod autoscaling (AKS)

```bash
kubectl autoscale deployment account-service -n opnbnk4 --cpu-percent=70 --min=2 --max=10
```

Repeat for `transaction-service`, `api-gateway`. Tune after observing Prometheus CPU metrics.

## Data layer

- **PostgreSQL:** Scale vertically on Azure Flexible Server; use read replicas for reporting (future).
- **Redis:** Azure Cache cluster tier for HA.
- **Kafka:** Increase partitions for hot topics before scaling consumers.

## Connection pools

- Hikari: adjust `maximum-pool-size` per instance count so total connections stay under Postgres `max_connections`.

## Compose (local)

```bash
docker compose up --scale account-service=3
```

Ensure gateway load-balances to multiple instances (Kubernetes Service does; Compose requires custom setup for multi-instance routing).
