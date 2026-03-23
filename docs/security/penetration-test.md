# Simulated penetration test — Day 4

**Scope:** OAuth2 gateway patterns, JWT validation posture, open banking access control, rate limiting, and secret hygiene in the **local Docker Compose** configuration. This is a **developer-led** assessment, not a third-party pentest.

## Methodology

- Review Spring Security and gateway filters for open redirects and scope handling.  
- Exercise open banking APIs with mismatched `X-Demo-Customer-Id` / consent IDs.  
- Observe gateway Redis rate limiter on `/openbanking/**` (Docker profile).  
- Grep repository for accidental secrets; confirm Vault/Key Vault path for production.

## Findings

| ID | Area | Severity | Finding | Status / mitigation |
|----|------|----------|---------|---------------------|
| PT-1 | OAuth2 / JWT | Info | Core APIs in Compose often run **without** strict resource-server issuer validation to keep smoke tests green. | **Production:** set `KEYCLOAK_ISSUER_URI` (or equivalent) on every service; enforce audience and clock skew. |
| PT-2 | Open banking | Medium (dev) | Demo headers (`X-Demo-Tpp-Id`, `X-Demo-Customer-Id`) are **not** a substitute for signed consent tokens. | Replace with Berlin Group–style consent records plus mTLS in staging. |
| PT-3 | Broken access control | Low | Automated tests assert consent mismatch returns **403/404** for AIS; re-run after schema changes. | Track in CI Playwright `api/banking-api.spec.ts`. |
| PT-4 | Rate limiting | Low | Gateway applies Redis limiter in Docker; verify **429** after burst (manual or JMeter). | Tune `replenishRate` / `burstCapacity` per route. |
| PT-5 | Dependencies | Variable | OWASP Dependency-Check plugin added per service (`./gradlew dependencyCheckAnalyze`). | Address CVSS 7+; use NVD API key for CI (`NVD_API_KEY`). |
| PT-6 | Container images | Variable | Run **Trivy** in CI / locally: `trivy image dbp-gateway:ci` after `docker build`. | Prefer slim base images; rebuild on CVE patches. |

## JWT checklist (production)

- [ ] Signature validated (JWKS from issuer).  
- [ ] `exp` and `nbf` enforced; short access token TTL.  
- [ ] `aud` / `azp` matches API resource.  
- [ ] No sensitive claims logged.

## OAuth2 client credentials (TPP)

- Store **client_id** / **client_secret** in Azure Key Vault or Vault KV; rotate on compromise.  
- Restrict redirect URIs explicitly; disallow wildcards in production.

## Retest cadence

- **Monthly:** dependency and image scans.  
- **Per release:** replay API E2E security cases; extend with ZAP/Burp in staging.
