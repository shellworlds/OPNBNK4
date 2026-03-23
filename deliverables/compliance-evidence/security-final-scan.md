# Final security scans (release v1.0.0+)

## OWASP Dependency-Check

```bash
export NVD_API_KEY=...   # recommended
./infrastructure/scripts/run-dependency-check-all.sh
```

Archive HTML reports from each service `build/reports/dependency-check-report.html` for the **client secure share**.

## Trivy (production images)

After `docker build` or pull from GHCR:

```bash
trivy image ghcr.io/shellworlds/opnbnk4-api-gateway:v1.0.0
```

Run GitHub Action **Security scan (manual)** and download SARIF from the run if enabled.

## Sign-off

| Scan | Date | Critical/High count | Owner |
|------|------|---------------------|--------|
| Dependency-Check | | | |
| Trivy gateway | | | |
| Trivy account-service | | | |

_Zero critical is the usual gate; document waivers with CVE ID and compensating control._
