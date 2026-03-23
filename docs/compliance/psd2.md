# PSD2-style open banking (evidence)

- **AIS / PIS** façade under `/openbanking/*` with consent checks.
- **Strong Customer Authentication:** delegated to Keycloak / future FIDO2 in channel apps.
- **TPP identification:** demo headers in dev; production requires OAuth2 client credentials + registry.

Gaps: eIDAS certificates, RTS detailed security measures, and regulatory reporting are out of scope for this reference implementation.
