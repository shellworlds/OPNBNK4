# Security overview

## Authentication

- **Customers**: Prefer **FIDO2 / WebAuthn** for phishing-resistant step-up and passwordless where policy allows; fallback to OIDC-backed credentials with risk-based challenges.
- **TPPs / service clients**: mTLS or **private_key_jwt** for client authentication; no long-lived shared secrets in production.
- **Staff / ops**: SSO with MFA; break-glass procedures documented separately.

## Encryption and key management

- **In transit**: TLS 1.2+ everywhere; mutual TLS for east-west service mesh (target state).
- **At rest**: Database TDE / volume encryption (cloud provider managed); application-level field encryption for highly sensitive attributes where required.
- **HSM / KMS**: Root keys and payment signing keys in **HSM-backed** or cloud **KMS** with strict IAM; no cleartext private keys in repos or images.

## Fraud detection (approach)

- **Real-time scoring** on login, payment initiation, and open banking consent grants.
- **Signals**: device, velocity, behavioral biometrics (where permitted), beneficiary risk lists, amount thresholds.
- **Response**: step-up auth, block, or manual review queue; all decisions logged for model tuning and audit.

## Baseline controls (Day 1 onward)

- Least-privilege service accounts, secrets from vault/Key Vault (not env files in prod).
- Centralized audit logging for security-relevant events.
- Dependency scanning and container image scanning in CI/CD (to be wired in subsequent days).

## Keycloak (Day 2 — development)

- **Image**: `quay.io/keycloak/keycloak:24.0.5` in Docker Compose, command `start-dev --import-realm`.
- **Realm**: `digital-banking` imported from `infrastructure/keycloak/digital-banking-realm.json`.
- **Clients**: `web-portal` (public, PKCE-friendly redirect to `http://localhost:3000/*`); `tpp-demo` (confidential, service account — **change the secret** for any shared environment).
- **Users**: `demo` / `demo` (lab only).
- **JWT for resource servers**: Spring Boot services accept `spring.security.oauth2.resourceserver.jwt.issuer-uri` when set (e.g. `KEYCLOAK_ISSUER_URI`). **Docker Compose leaves issuer unset** on core services so smoke scripts and integration tests can call APIs without tokens; enable issuer in hardened environments.
- **Token flow**: Browser uses authorization code + PKCE via Keycloak JS; the gateway forwards the `Authorization` header to upstreams by default (no separate token-relay filter required for pass-through).

## HSM simulation and Azure Key Vault (Day 2)

- **Simulation**: Sensitive signing or encryption material must **not** live in Git. For local development, use **environment variables** or Docker secrets injected at runtime; treat them as ephemeral.
- **Azure Key Vault integration (target)**:
  1. Provision a Key Vault and RBAC roles for workload identity (managed identity for AKS / App Service / Container Apps).
  2. Store keys and certificates in Key Vault; reference them from Spring via **Azure Identity** + **Key Vault Secrets** / **Certificates** clients, or mount CSI driver secrets in Kubernetes.
  3. Rotate keys with dual-signing windows; log rotation events to centralized audit.
  4. Replace the `tpp-demo` client secret and any DB passwords with Key Vault references in non-dev environments.

## FIDO2

See [FIDO2 placeholder](../security/fido2.md) for the planned WebAuthn approach (not implemented in Day 2).
