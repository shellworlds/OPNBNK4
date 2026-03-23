# Pre-deployment checklist

- [ ] Secrets in **Azure Key Vault** (or Vault); no plaintext in git
- [ ] **PostgreSQL** backups / PITR enabled (Azure automated backups)
- [ ] **TLS** on ingress (cert-manager + Let's Encrypt or managed certificate)
- [ ] **Prometheus** rules + **Alertmanager** routes (PagerDuty / Slack webhook)
- [ ] **Rollback** plan tested (`helm rollback` / `kubectl rollout undo`)
- [ ] **Resource requests/limits** set on all deployments
- [ ] **Network policies** (optional) restricting east-west traffic
- [ ] **DR**: restore test documented in `disaster-recovery.md`
