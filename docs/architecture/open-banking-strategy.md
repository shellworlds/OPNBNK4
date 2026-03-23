# Open banking strategy

## PSD2 alignment (conceptual)

The platform is designed to support **Account Information Services (AIS)** and **Payment Initiation Services (PIS)** with explicit **customer consent**, strong customer authentication (SCA), and secure TPP identification — consistent with PSD2-style regulation (exact jurisdictional rules to be confirmed with legal/compliance).

## AIS / PIS APIs

| Stream | Purpose | Example capabilities |
|--------|---------|----------------------|
| **AIS** | Read balances, transactions, account metadata | Consent-scoped account list, transaction history with pagination |
| **PIS** | Initiate payments from funded accounts | Single immediate payment, status polling, refund/cancellation policies |

Implementation will expose versioned REST (and optionally JSON-LD where required) under routes such as `/api/openbanking/...`, with **idempotency keys** on mutating calls.

## OAuth2 / OIDC flows

- **Authorization Code + PKCE** for interactive customer consent (web/mobile).
- **Client credentials** (with mTLS or private key JWT) for confidential TPP server-to-server where applicable.
- **Scopes** map to consent artifacts (e.g. `accounts:read`, `payments:initiate`).
- **Tokens** are short-lived; **refresh** only where product policy allows; bind tokens to **consent IDs** and **TPP client IDs**.

## Consent management

- **Consent record**: who (customer), what (scopes, accounts), when (valid from/until), which TPP, revocation status.
- **Lifecycle**: grant, refresh scope (re-auth), revoke, expire; immutable audit trail for regulatory evidence.
- **UX**: Clear disclosure of data access and payment limits before approval.

## Operational controls

- TPP onboarding: registry identifiers, certificates, redirect URI allow lists.
- Rate limiting and anomaly detection at gateway and open banking service (expanded in later days).
