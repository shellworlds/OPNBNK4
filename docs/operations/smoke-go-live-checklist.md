# Smoke test & go-live checklist

Use before announcing production.

- [ ] Web portal loads over **HTTPS**
- [ ] Mobile app points at production gateway URL
- [ ] New user can register / login (Keycloak)
- [ ] Biometric login (**planned** — see FIDO2 doc)
- [ ] Account balances match source of truth
- [ ] Transaction list loads (pagination if enabled)
- [ ] Payment completes for small amount; **UNDER_REVIEW** for high EUR amount
- [ ] Fraud **BLOCK** for extreme amount (test env only)
- [ ] TPP consent + AIS + PIS + revoke
- [ ] Grafana shows healthy metrics
- [ ] All `/actuator/health` **UP**
- [ ] DB backups scheduled
- [ ] Synthetic alert fires in non-prod to validate routing

Sign-off: link to `docs/uat/sign-off-placeholder.md` or ticket ID.
