# Disaster recovery — PostgreSQL and platform state

## Scope

This document describes **backup and restore** for the transactional store (PostgreSQL) and operational expectations on **Azure** (managed database). Application configuration is Git-backed; rebuild images from CI.

## Azure Database for PostgreSQL (recommended production)

1. **Backups:** Enable **automated backups** (geo-redundant optional). Point-in-time restore (PITR) is available per Azure SKU.  
2. **Restore:** Create a new server instance from backup; update connection strings in Key Vault and Kubernetes ConfigMaps; roll workloads.  
3. **RTO/RPO:** Set with stakeholders (e.g. RPO 5 minutes with frequent WAL, RTO 1 hour with runbook rehearsal).

## Docker Compose (development)

- Data lives in the **`postgres_data`** named volume.  
- **Backup:**  
  `docker compose exec -T postgres pg_dump -U bank bank > backup-$(date -u +%Y%m%d).sql`  
- **Restore:**  
  `docker compose exec -T postgres psql -U bank -d bank < backup-YYYYMMDD.sql`  
  (Stop writers first; or restore to a fresh volume.)

## Kafka / Redis

- **Kafka:** Treat as **replayable** if Postgres is source of truth; otherwise snapshot per organizational policy (Confluent/Azure Event Hubs).  
- **Redis:** Ephemeral cache; safe to rebuild; warm caches after restore.

## Failover test (quarterly)

1. Restore latest backup to an isolated database.  
2. Point **one** staging service replica at the restored instance.  
3. Run Playwright API suite (`tests/e2e`, project `api`).  
4. Document duration and anomalies in `docs/reports/`.

## Contacts

- Platform owner: _fill_  
- DBA / cloud ops: _fill_
