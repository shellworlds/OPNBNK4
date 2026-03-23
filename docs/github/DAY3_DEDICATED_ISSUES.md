# Dedicated GitHub issues — Day 3 closure & Day 4 backlog

Create **Issues** at **[github.com/shellworlds/OPNBNK4/issues](https://github.com/shellworlds/OPNBNK4/issues)**. Link each to the **Project** board (**[Projects](https://github.com/shellworlds/OPNBNK4/projects)** / **[board #6](https://github.com/users/shellworlds/projects/6)**). Use **Milestone** `Day 3` for closure and `Day 4` for backlog.

Suggested **labels:** `day-3`, `day-4`, `documentation`, `enhancement`, `security`, `testing`, `open-banking`, `observability`.

**Issue templates:** [New issue → Choose](https://github.com/shellworlds/OPNBNK4/issues/new/choose) — includes Day 3 wiki sync, Day 4 WebAuthn/observability, plus existing Day 3 OpenAPI / OAuth2 templates.

---

## Day 3 — closure (mark **Done** on board)

### Issue D3-C1 — Day 3 delivery accepted (documentation)

**Title:** `[Day 3] Delivery complete — link client handoff`

**Body:**

```markdown
## Summary
Day 3 scope delivered on `main`. Client submission: `docs/submission/CLIENT_HANDOFF_DAY3.md`.

## Links
- https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY3.md
- https://github.com/shellworlds/OPNBNK4/blob/main/DAY3_COMPLETE.md

## Acceptance
- [ ] Reviewer has repository URL and handoff doc
- [ ] CI green on `main`
- [ ] Release `v0.3.0-day3` published (optional but recommended)
```

---

### Issue D3-C2 — Wiki synced for Day 3

**Title:** `[Day 3] Sync docs/wiki to GitHub Wiki (Day-3-Delivery + hub pages)`

**Body:**

```markdown
## Task
Push `docs/wiki/` updates to `OPNBNK4.wiki.git` so live wiki matches repo.

## Pages touched
- Day-3-Delivery (new)
- Home, _Sidebar, Releases, GitHub-Project-Issues-Actions-Insights

## Acceptance
- [ ] Wiki pages visible at https://github.com/shellworlds/OPNBNK4/wiki
```

---

## Day 4 — backlog (Todo / In Progress)

### Issue D4-1 — WebAuthn / FIDO2

**Title:** `[Day 4] WebAuthn passkeys — authenticators table, JWT, web portal`

**Body:** See `DAY3_COMPLETE.md` → Blockers. Align with `docs/security/fido2.md`.

---

### Issue D4-2 — Vault / Azure Key Vault

**Title:** `[Day 4] Secrets: HashiCorp Vault (dev Compose) + Azure Key Vault doc + service integration`

---

### Issue D4-3 — Aggregated OpenAPI + developer portal

**Title:** `[Day 4] springdoc on services + gateway /docs + frontend/developer-portal`

---

### Issue D4-4 — Load & performance

**Title:** `[Day 4] JMeter plan + load test job in CI (optional)`

---

### Issue D4-5 — Observability stack

**Title:** `[Day 4] OpenTelemetry/Micrometer + Prometheus + Grafana + JSON logs / ELK in Compose`

---

### Issue D4-6 — Container registry

**Title:** `[Day 4] GHCR: build and push images on merge to main`

---

### Issue D4-7 — JWT on core APIs in Compose

**Title:** `[Day 4] OAuth2 resource server on account/transaction/openbanking in docker-compose + smoke with token`

*(Carried from earlier backlog if not yet done.)*

---

## Prior reference

Day 2 checklist (historical): [DAY2_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY2_DEDICATED_ISSUES.md)
