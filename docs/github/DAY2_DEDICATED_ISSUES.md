# Dedicated GitHub issues — Day 2 closure & Day 3 backlog

Use this list to create **Issues** at [github.com/shellworlds/OPNBNK4/issues](https://github.com/shellworlds/OPNBNK4/issues). Link each to the **Project** board ([Projects](https://github.com/shellworlds/OPNBNK4/projects) / [board #6](https://github.com/users/shellworlds/projects/6)) and set **Milestone** `Day 2` (closed items) or `Day 3` (open backlog).

Suggested **labels:** `day-2`, `day-3`, `documentation`, `enhancement`, `security`, `testing`, `open-banking`.

---

## Day 2 — closure (mark Done on board)

### Issue D2-1 — Day 2 delivery accepted (documentation)

**Title:** `[Day 2] Delivery complete — link client handoff`

**Body:**

```markdown
## Summary
Day 2 scope delivered on `main`. Client submission: `docs/submission/CLIENT_HANDOFF_DAY2.md`.

## Links
- https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY2.md
- https://github.com/shellworlds/OPNBNK4/blob/main/DAY2_COMPLETE.md

## Acceptance
- [ ] Reviewer has repository URL and handoff doc
- [ ] CI green on `main`
```

---

### Issue D2-2 — Wiki synced for Day 2

**Title:** `[Day 2] Sync docs/wiki to GitHub Wiki (Day-2-Delivery + GitHub hub page)`

**Body:**

```markdown
## Task
Push `docs/wiki/` updates to `OPNBNK4.wiki.git` so live wiki matches repo.

## Pages touched
- Day-2-Delivery
- Home, _Sidebar, Releases, GitHub-Project-Issues-Actions-Insights

## Acceptance
- [ ] Wiki pages visible at https://github.com/shellworlds/OPNBNK4/wiki
```

---

## Day 3 — backlog (Todo / In Progress)

### Issue D3-1 — Enable JWT on core services in Compose + smoke

**Title:** `[Day 3] OAuth2 resource server on account/transaction/openbanking in docker-compose`

**Body:** See `DAY2_COMPLETE.md` → Known gaps.

---

### Issue D3-2 — Publish OpenAPI specs

**Title:** `[Day 3] OpenAPI 3 YAML for gateway-routed APIs + CI check`

---

### Issue D3-3 — Kafka + Keycloak Testcontainers matrix

**Title:** `[Day 3] Expand integration tests: Kafka + Keycloak containers in CI`

---

### Issue D3-4 — Restrict `GET /api/accounts` (list all)

**Title:** `[Day 3] Remove or admin-gate list-all accounts endpoint`

---

### Issue D3-5 — Dependabot / dependency graph

**Title:** `[Day 3] Enable Dependabot + dependency insights for client audit`

**Body:** Settings → Code security; link from Insights.

---

## Issue templates in repo

Use **New issue → templates** on GitHub: [github.com/shellworlds/OPNBNK4/issues/new/choose](https://github.com/shellworlds/OPNBNK4/issues/new/choose). Source files: [`.github/ISSUE_TEMPLATE/`](https://github.com/shellworlds/OPNBNK4/tree/main/.github/ISSUE_TEMPLATE).
