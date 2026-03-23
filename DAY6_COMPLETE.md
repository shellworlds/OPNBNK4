# Day 6 complete — post-deploy validation, training, final acceptance

**Repository:** https://github.com/shellworlds/OPNBNK4

## Summary

- **Operations:** Post-deployment validation checklist, alert validation guide, incident response, rollback procedure; runbook updated with Day 6 steps and support table.
- **Issues:** Template **Post-deployment defect** for production/staging triage.
- **Training:** Slide outline (`docs/training/training-slides.md`), hands-on script, FAQ log; video instructions under `deliverables/training/` (binary gitignored).
- **Compliance evidence:** Index + GDPR/PSD2/security scan procedure in `deliverables/compliance-evidence/`.
- **API handover:** Postman starter collection; Swagger UI offline README.
- **Architecture exports:** PDF/PNG export instructions in `deliverables/architecture/README.md`.
- **Acceptance:** `docs/submission/FINAL_ACCEPTANCE_CHECKLIST.md` plus `deliverables/ACCEPTANCE_LETTER.md`.

## Not performed in-repo (environment-dependent)

- Live kubectl / TLS checks against your cluster — run locally using `post-deployment-validation.md`.
- Actual 15-minute training-video.mp4 — record per `deliverables/training/README.md`.
- Static Swagger HTML bundle — blocked until OpenAPI JSON is exported per service.

## Client handoff

`docs/submission/CLIENT_HANDOFF_DAY6.md`
