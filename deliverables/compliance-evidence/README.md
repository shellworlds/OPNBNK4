# Compliance evidence pack (index)

This folder holds **evidence descriptions** and pointers. Raw exports (SQL dumps, scans) may contain PII — store in a **secure client share**, not in public git.

| File | Purpose |
|------|---------|
| `gdpr-evidence.md` | Consent/export/deletion evidence checklist |
| `psd2-evidence.md` | AIS/PIS/OAuth2/TPP checklist |
| `security-final-scan.md` | How to run OWASP + Trivy on release images |

## Generating attachments

1. **Consent sample:** run read-only SQL on `openbanking` consent tables; redact customer IDs; save PDF in client DMS.
2. **Export test:** curl or Postman `GET /api/user/export` — save HTTP trace (redact).
3. **Deletion:** document `POST /api/user/delete-request` response and planned batch job.
4. **Security:** attach Trivy/Dependency-Check HTML from CI artifact or local run.
