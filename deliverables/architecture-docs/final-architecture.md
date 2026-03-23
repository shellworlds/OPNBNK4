# Final architecture — OPNBNK4

This document consolidates the **as-built** view for the digital banking platform. For historical evolution see `high-level-design.md` and `decision-log.md`.

## System context

Customers, bank staff (limited), and **TPPs** interact through:

- **Web portal** (React) and **mobile** (Expo / static export) → **API Gateway**
- **TPP integrations** → same gateway with open banking routes

Identity: **Keycloak** (OIDC). Data: **PostgreSQL** per bounded context, **Redis** for cache and gateway rate limiting, **Kafka** for events.

## Service map

| Service | Port (Compose) | Responsibility |
|---------|----------------|----------------|
| api-gateway | 8080 | Route, CORS, channel validation, circuit breakers, rate limits |
| account-service | 8081 | Accounts, balances, GDPR export/delete-request stubs, core simulator enrichment |
| transaction-service | 8082 | Transactions, fraud sync check, `UNDER_REVIEW` for high-value rule hits |
| openbanking-service | 8083 | Consents, AIS/PIS façade |
| fraud-detection-service | 8088 | Rule engine (EUR thresholds, device heuristics) |
| notification-service | (internal) | Kafka consumer, optional MailHog email |
| core-simulator | 8087 | Core banking stubs |

## Key flows

1. **Payment (domestic):** Client → gateway → transaction-service → fraud (sync) → DB + Kafka events.  
2. **PIS:** openbanking-service → transaction create + complete → returns real `status` (`COMPLETED` or `UNDER_REVIEW`).  
3. **AIS:** Consent filter → account-service / transaction-service downstream.

## Observability

Prometheus scrapes Actuator; Grafana on 3001; Jaeger for traces (OTLP rollout incremental). MailHog captures dev email.

## Deployment target

Kubernetes (`infrastructure/k8s/base`), Helm skeleton (`infrastructure/helm/opnbnk4`), Terraform stubs for Azure AKS + managed data services.

## Diagram (containers)

See mermaid diagrams in `high-level-design.md` (Day 3–4 sections) — single source for sequence charts.
