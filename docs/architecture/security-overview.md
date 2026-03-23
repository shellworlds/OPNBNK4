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
