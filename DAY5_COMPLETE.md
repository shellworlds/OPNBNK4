# Day 5 complete — UAT, polish, production handover

**Production tag:** `v1.0.0`  
**Repository:** https://github.com/shellworlds/OPNBNK4

## What shipped

- **UAT pack:** `docs/uat/uat-script.md`, `uat-results.md`, `sign-off-placeholder.md`.
- **Fraud / payments:** Transactions with fraud verdict **REVIEW** move to **`UNDER_REVIEW`** (DB migration `V3`); PIS returns real completion status from transaction service.
- **UI:** Loading spinner, clearer API errors, mock login submit state, **UNDER_REVIEW** highlight in transaction list.
- **E2E:** Playwright case for EUR 6,000 domestic payment → `UNDER_REVIEW`.
- **Documentation:** Final architecture, API index, deployment / monitoring / scaling / troubleshooting, pre-deploy + smoke checklists, developer guides, user FAQ, compliance drafts, performance final-tuning notes, updated DR drill section.
- **Deliverables:** `deliverables/` manifest with copies of k8s, terraform, UAT, performance, security, architecture.
- **Reports:** `GO_LIVE.md`, `PROJECT_COMPLETION_REPORT.md`.
- **CI:** `day5-verify.yml` (manual).

## Not done in-repo (external)

- Actual **Azure AKS** deploy and **kubectl** verification (needs subscription + secrets).
- **demo.mp4** — record locally per `deliverables/demo/README.md` (binary gitignored).
- **PDF** exports — generate from Markdown client-side if required.

## Client handoff

`docs/submission/CLIENT_HANDOFF_DAY5.md`
