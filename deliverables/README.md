# Client deliverables bundle (manifest)

This folder mirrors key artifacts for handover. **Authoritative sources** remain in `docs/` and `infrastructure/`.

| Subfolder | Contents |
|-----------|----------|
| `architecture-docs/` | High-level + final architecture copies |
| `api-specs/` | Placeholder README — add exported OpenAPI JSON |
| `deployment-manifests/` | Snapshot of `k8s/base` and `terraform/` |
| `uat-results/` | UAT results + sign-off template |
| `performance-report/` | Performance report copy |
| `security-audit/` | Penetration test notes copy |
| `demo/` | Screen recording instructions (no binary committed) |
| `training/` | 15-minute training video script + README (mp4 gitignored) |
| `compliance-evidence/` | GDPR / PSD2 / security scan evidence index |
| `postman/` | Starter Postman collection for gateway |
| `architecture/` | How to export C4/sequence diagrams to PDF/PNG |
| `swagger-ui/` | Offline Swagger UI instructions |
| `ACCEPTANCE_LETTER.md` | Client sign-off template |

## Demo video

Record ~5 minutes covering portal login, account view, payment, Grafana, Postman open banking call. Save as **`deliverables/demo/demo.mp4`** locally (gitignored if large) or host on your CDN and link here.

## Training video (Day 6)

See **`deliverables/training/README.md`**. Optional root filename: **`deliverables/training-video.mp4`** (gitignored).

## Final acceptance

Checklist: **`docs/submission/FINAL_ACCEPTANCE_CHECKLIST.md`** · Certificate: **`ACCEPTANCE_LETTER.md`**.
