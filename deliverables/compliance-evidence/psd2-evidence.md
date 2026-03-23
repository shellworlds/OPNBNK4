# PSD2-style open banking — evidence checklist

| Topic | Evidence | Notes |
|-------|----------|--------|
| AIS | Endpoint list + successful call with valid consent | `/openbanking/accounts`, transactions |
| PIS | Payment initiation + status propagation | `/openbanking/payments` returns downstream status |
| Consent revocation | 403/404 after revoke | E2E + smoke |
| Strong customer authentication | Keycloak login; FIDO2 roadmap | `docs/security/fido2.md` |
| TPP identification | OAuth2 client credentials (prod) | Demo headers in dev only |

Attach redacted HTTP transcripts or Postman exports to your compliance folder.
