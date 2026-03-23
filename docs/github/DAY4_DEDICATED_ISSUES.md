# Dedicated GitHub issues — Day 4 closure and Day 5 backlog

Create **Issues** at **[github.com/shellworlds/OPNBNK4/issues](https://github.com/shellworlds/OPNBNK4/issues)**. Link each to the **Project** board (**[Projects](https://github.com/shellworlds/OPNBNK4/projects)** / **[board #6](https://github.com/users/shellworlds/projects/6)**). Use **Milestone** `Day 4` for closure and `Day 5` for backlog.

**Labels:** `day-4`, `day-5`, `documentation`, `enhancement`, `security`, `testing`, `open-banking`, `observability`, `deployment`.

---

## Day 4 — closure (mark **Done** on board)

### Issue D4-C1 — Day 4 delivery accepted (documentation)

**Title:** `[Day 4] Delivery complete — link client handoff`

**Body:**

```markdown
## Summary
Day 4 scope delivered on `main`. Client submission: `docs/submission/CLIENT_HANDOFF_DAY4.md`.

## Links
- https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY4.md
- https://github.com/shellworlds/OPNBNK4/blob/main/DAY4_COMPLETE.md
- Tag: `v0.9.0`

## Acceptance
- [ ] Reviewer has repository URL and handoff doc
- [ ] CI green on `main` (includes Playwright API E2E)
```

---

### Issue D4-C2 — Wiki synced for Day 4

**Title:** `[Day 4] Sync docs/wiki to GitHub Wiki (Day-4-Delivery + hub pages)`

**Body:**

```markdown
## Task
Push `docs/wiki/` updates to `OPNBNK4.wiki.git` so live wiki matches repo.

## Pages touched
- Day-4-Delivery (new)
- Home, _Sidebar, Releases, GitHub-Project-Issues-Actions-Insights

## Acceptance
- [ ] Wiki pages visible at https://github.com/shellworlds/OPNBNK4/wiki
```

---

### Issue D4-C3 — Insights and Dependabot review

**Title:** `[Day 4] Insights — dependency graph, Dependabot, security alerts`

**Body:**

```markdown
## Task
In GitHub **Insights** / **Dependency graph**, confirm Gradle, npm, and Actions ecosystems. Review Dependabot PRs. Optional: enable Dependabot security updates.

## Docs
- docs/github/INSIGHTS_DEPENDABOT.md
```

---

## Day 5 — backlog (Todo / In Progress)

### Issue D5-1 — Staging AKS + enable Helm job

**Title:** `[Day 5] Enable deploy-aks helm-upgrade job; Azure Key Vault CSI`

---

### Issue D5-2 — SonarCloud / quality gate

**Title:** `[Day 5] Enable sonar-quality.yml; fix critical smells; coverage thresholds`

---

### Issue D5-3 — OpenAPI + developer portal

**Title:** `[Day 5] Aggregated OpenAPI + Swagger UI in developer-portal`

---

### Issue D5-4 — Full JWT on Compose smoke

**Title:** `[Day 5] Token-aware smoke + Playwright web flows (Keycloak)`

---

### Issue D5-5 — Performance proof on AKS

**Title:** `[Day 5] JMeter against staging; paste results into docs/performance/report.md`
