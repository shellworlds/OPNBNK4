# End-user guide — web and mobile (Day 4 draft)

This guide is **text-first** for the release candidate. Replace bracketed placeholders with screenshots when publishing to clients.

## Web portal (`http://localhost:3000` in Compose)

1. **Sign in** — Use Keycloak credentials from the imported realm (`digital-banking`). [Screenshot: login screen]  
2. **Dashboard** — View linked accounts and balances. [Screenshot: dashboard]  
3. **Account detail** — Open an account to see transactions. [Screenshot: transactions list]  
4. **Sign out** — Use the portal logout control to clear the session.

## Mobile (Expo / static export)

1. Open the **mobile-web** container URL from Compose (`http://localhost:3010`) or run the Expo app from `frontend/mobile-app`.  
2. Sign in with the same realm users where OIDC is configured. [Screenshot: mobile home]  
3. Review accounts and transactions; biometric flows are **planned** (see `docs/security/fido2.md`).

## Open banking (customer view)

- **Consents** — Managed via API in this milestone; a customer-facing consent UI may be added in Day 5–7.  
- **Support** — Contact your bank using the channel printed on your card (placeholder).

## Data rights (GDPR stubs)

- **Export:** operators may call `GET /api/user/export?customerId=…` (account-service) for a JSON snapshot.  
- **Erasure request:** `POST /api/user/delete-request?customerId=…` records intent; full anonymization is a backend pipeline follow-up.

## Troubleshooting

| Symptom | Check |
|---------|--------|
| Blank dashboard | Gateway URL in portal build args; browser console errors |
| 401 on API | Keycloak token expiry; refresh login |
| Rate limited (429) | Reduce automation frequency; wait for Redis bucket refill |
