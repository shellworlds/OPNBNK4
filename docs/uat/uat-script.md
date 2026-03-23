# User acceptance testing (UAT) script — Day 5

**Product:** OPNBNK4 digital banking platform  
**Environment:** Docker Compose (local) / staging URL when configured  
**Channel headers:** Use `X-Client-Id`, `X-Client-Channel` (e.g. `automation`) on gateway routes when validation is enabled.

## Test case 1 — Customer onboarding

| Step | Action | Expected result |
|------|--------|-----------------|
| 1.1 | Open web portal (`http://localhost:3000`), sign in via Keycloak | User authenticated; session active |
| 1.2 | FIDO2 / WebAuthn enrollment | **Planned:** see `docs/security/fido2.md` — not fully wired in Compose demo |
| 1.3 | Open mobile web or Expo app (`mobile-web` / Expo) | App loads; same realm login where configured |
| 1.4 | Dashboard | Accounts listed for configured customer id; balances match API |

**Evidence:** Screenshot of dashboard; network tab 200 on `/api/accounts/customer/...`

## Test case 2 — Account management

| Step | Action | Expected result |
|------|--------|-----------------|
| 2.1 | Open account detail; view transactions | List shows `PENDING` / `COMPLETED` / `UNDER_REVIEW` as applicable |
| 2.2 | Filter by date range | **Stub:** add client-side filter or API query param in future sprint |
| 2.3 | Download statement PDF | **Stub:** document as not implemented — use export JSON `GET /api/user/export` as interim |
| 2.4 | Update profile | **Stub:** Keycloak account console or future profile API |

## Test case 3 — Payments

| Step | Action | Expected result |
|------|--------|-----------------|
| 3.1 | Create domestic payment (web) via API or future UI | Transaction `PENDING` then `COMPLETED` after `/complete` |
| 3.2 | Mobile payment | Same API through gateway with mobile channel header |
| 3.3 | Verify history | Status `COMPLETED` for normal amounts |
| 3.4 | Payment with amount **over €5,000** (EUR) | After complete, status **`UNDER_REVIEW`** (fraud rule engine `REVIEW`) |
| 3.5 | Payment blocked | Amount over **€50,000** EUR equivalent → HTTP **403** on complete |

## Test case 4 — Open banking (TPP)

| Step | Action | Expected result |
|------|--------|-----------------|
| 4.1 | Developer portal TPP registration | **Stub:** use demo headers `X-Demo-Tpp-Id` per smoke/E2E |
| 4.2 | OAuth2 client credentials | **Partial:** Keycloak realm; full TPP client registry TBD |
| 4.3 | Create consent | `POST` consent; receive consent id |
| 4.4 | AIS accounts/transactions | `GET` with valid consent returns data |
| 4.5 | PIS payment | `POST /openbanking/payments` returns `transactionId` and `status` from transaction service (`COMPLETED` or `UNDER_REVIEW`) |
| 4.6 | Revoke consent | Subsequent AIS returns **403/404** |

## Test case 5 — Admin / operations

| Step | Action | Expected result |
|------|--------|-----------------|
| 5.1 | Grafana (`http://localhost:3001`) | Dashboards/data sources reachable |
| 5.2 | Fraud alerts | Check fraud service logs / future Grafana panel |
| 5.3 | Stop `account-service` container | Gateway circuit breaker / fallback JSON (Day 3 behaviour) |

## Test case 6 — Security

| Step | Action | Expected result |
|------|--------|-----------------|
| 6.1 | Invalid login (Keycloak) | Lockout policy per realm (configure **Max login failures** in Keycloak for “3 attempts”) |
| 6.2 | API without token (when JWT enforced) | **401** — in open Compose profile many routes stay open; document production expectation |
| 6.3 | Expired token | **401** after refresh failure |

---

**Executor:** _name / role_  
**Date:** _YYYY-MM-DD_
