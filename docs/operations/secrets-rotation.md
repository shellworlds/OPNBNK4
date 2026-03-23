# Secrets rotation — OPNBNK4

## Principles

- **No hardcoded secrets** in application code; use environment variables injected at runtime.  
- **Development:** Docker Compose `.env` (gitignored) or shell exports — never commit real passwords.  
- **Production (Azure):** Azure Key Vault with the **Secrets Store CSI driver** or workload identity; reference secrets from Kubernetes `Secret` objects synced by the provider.

## Services and typical secrets

| Secret | Consumers | Rotation trigger |
|--------|-----------|------------------|
| Postgres `POSTGRES_PASSWORD` | All JDBC services | Quarterly or on staff change |
| Keycloak admin | Keycloak container | On admin offboarding |
| Kafka (SASL/SSL in prod) | All Spring `spring.kafka` clients | Cert expiry or policy |
| OAuth2 client secrets (TPP) | Auth server / gateway | Per TPP contract or leak |
| Redis password | Gateway, account-service | Quarterly |

## Rotation procedure (generic)

1. **Generate** a new secret in Key Vault (new version).  
2. **Dual-write window:** if supported, add new credential alongside old on consumers.  
3. **Roll pods** (`kubectl rollout restart deployment/…`) so processes pick up mounted secrets.  
4. **Revoke** old secret version in Key Vault after health checks pass.

## HashiCorp Vault (optional dev parity)

1. Enable KV v2 at `secret/opnbnk4`.  
2. Path `secret/data/opnbnk4/postgres` with keys `username`, `password`.  
3. Use **Vault Agent** or init container to render `application-secrets.properties` — do not bake into images.

## Compose (local only)

```bash
cp .env.example .env   # if provided; edit values locally
docker compose up --build
```

Rotate by editing `.env` and recreating containers: `docker compose up -d --force-recreate`.

## Verification

- Confirm apps start with **no** secrets in logs (`logging.level.org.springframework.boot.autoconfigure=INFO` only).  
- Run `git grep -i password` in CI should only hit docs and examples.
