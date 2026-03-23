# FIDO2 / WebAuthn (placeholder)

## Status

Day 2 records the **intended** approach only. No FIDO2 or WebAuthn endpoints are implemented in services yet; retail login today uses **Keycloak OIDC** (with optional mock login when Keycloak is not configured in the web portal).

## Target architecture

- **Relying party**: Bank-owned domain (`web-portal` and API gateway as policy enforcement).
- **Authenticator**: Platform (passkeys), roaming security keys, or hybrid (phone as authenticator) per WebAuthn L2+.
- **Binding**: Link WebAuthn credentials to the same IAM subject as OIDC (Keycloak user or external IdP) with attestation policy and recovery flows.

## Implementation checklist (later sprint)

1. Expose registration and authentication ceremony endpoints (challenge storage, allow-list of origins, RP ID alignment with TLS hostnames).
2. Store credential public keys and sign counters in a dedicated table or IAM extension; never store private keys.
3. Integrate with **step-up** for high-risk actions (open banking consent, large payments).
4. Align with PSD2 SCA where jurisdiction requires it (dynamic linking for payments is separate from WebAuthn but often combined in UX).

## References

- [W3C Web Authentication](https://www.w3.org/TR/webauthn-3/)
- [FIDO Alliance](https://fidoalliance.org/)
