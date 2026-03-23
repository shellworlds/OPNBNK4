# Project complete — Days 1–6

← [Home](Home)

**Status:** Delivery phases **Day 1 through Day 6** are documented and implemented in **`shellworlds/OPNBNK4`** (code, CI, handoff packs). Use this page to align the **GitHub Project board**, **milestones**, **issues**, and **wiki** with that closure state. Optional **Day 7** work (retrospective, BAU handoff, archive) is tracked separately — see [Dedicated issues — Day 6 / Day 7](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY6_DEDICATED_ISSUES.md).

## Delivery timeline (wiki)

| Phase | Wiki | Tag / release (repo) |
|-------|------|----------------------|
| Day 1 | [Day 1 — Delivery](Day-1-Delivery) | [v0.1.0-day1](https://github.com/shellworlds/OPNBNK4/releases/tag/v0.1.0-day1) |
| Day 2 | [Day 2 — Delivery](Day-2-Delivery) | [v0.2.0-day2](https://github.com/shellworlds/OPNBNK4/releases/tag/v0.2.0-day2) |
| Day 3 | [Day 3 — Delivery](Day-3-Delivery) | [v0.3.0-day3](https://github.com/shellworlds/OPNBNK4/releases/tag/v0.3.0-day3) |
| Day 4 | [Day 4 — Delivery](Day-4-Delivery) | [v0.9.0](https://github.com/shellworlds/OPNBNK4/releases/tag/v0.9.0) |
| Day 5 | [Day 5 — Delivery](Day-5-Delivery) | [v1.0.0](https://github.com/shellworlds/OPNBNK4/releases/tag/v1.0.0) |
| Day 6 | [Day 6 — Delivery](Day-6-Delivery) | [v1.0.1-day6](https://github.com/shellworlds/OPNBNK4/releases/tag/v1.0.1-day6) |

Full release notes: [Releases](Releases) · [CHANGELOG.md](https://github.com/shellworlds/OPNBNK4/blob/main/CHANGELOG.md).

## GitHub Project board (Project #6)

**Board:** [OPNBNK4 Digital Banking Platform](https://github.com/users/shellworlds/projects/6) · [Repository projects](https://github.com/shellworlds/OPNBNK4/projects).

**Recommended closure state**

1. **Columns:** Move all items that belong to Days 1–6 to **Done** (or equivalent “Closed” column).
2. **Milestones:** Ensure milestones **Day 1** … **Day 6** exist; close each when its issues are finished. Create **Day 7** only if you continue BAU / closure tasks.
3. **Labels:** Keep `day-1` … `day-6` for filtering history; use `documentation`, `operations`, `bug` as appropriate for post-deploy tickets.

Copy-paste issue titles and backlog pointers: **[docs/github/PROJECT_COMPLETE_GITHUB.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/PROJECT_COMPLETE_GITHUB.md)** (repo) and [DAY6_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY6_DEDICATED_ISSUES.md).

## Issues & templates

| Resource | URL |
|----------|-----|
| **Issues** | [github.com/shellworlds/OPNBNK4/issues](https://github.com/shellworlds/OPNBNK4/issues) |
| **New issue (templates)** | [issues/new/choose](https://github.com/shellworlds/OPNBNK4/issues/new/choose) |
| **Post-deployment defect** (form) | [issues/new?template=post-deployment-defect.yml](https://github.com/shellworlds/OPNBNK4/issues/new?template=post-deployment-defect.yml) |

**Dedicated issue lists (by phase)**

- [DAY2_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY2_DEDICATED_ISSUES.md) (historical)
- [DAY3_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY3_DEDICATED_ISSUES.md)
- [DAY4_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY4_DEDICATED_ISSUES.md)
- [DAY5_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY5_DEDICATED_ISSUES.md)
- [DAY6_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY6_DEDICATED_ISSUES.md)

## Actions (CI/CD)

**Runs:** [github.com/shellworlds/OPNBNK4/actions](https://github.com/shellworlds/OPNBNK4/actions).

| Workflow file | When it runs | Purpose |
|---------------|----------------|---------|
| `ci.yml` | `push` / `pull_request` to `main` | Gradle per service, Postgres integration, npm, Docker, Compose smoke, Playwright API E2E |
| `day1-verify.yml` | Manual | Day 1 service set + optional E2E |
| `day2-verify.yml` | Manual | Day 2 set + `*Postgres*IntegrationTest` + optional E2E |
| `day3-verify.yml` | Manual | Full backend set + Postgres IT + optional E2E |
| `day4-verify.yml` | Manual | Same gates + optional Compose / Playwright / Trivy FS |
| `day5-verify.yml` | Manual | Full sweep for go-live / regression (use after Day 6 as well) |
| `security-scan.yml` | Manual | Trivy filesystem (SARIF) + gateway image scan |
| `deploy-aks.yml` | On **version tag** | Build/push images to GHCR; Helm job gated until Azure secrets |
| `sonar-quality.yml` | Stub | Enable when `SONAR_TOKEN` is set |

Detail: [GitHub: Project, Issues, Actions & Insights](GitHub-Project-Issues-Actions-Insights).

## Acceptance & handoff (client)

- [FINAL_ACCEPTANCE_CHECKLIST.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/FINAL_ACCEPTANCE_CHECKLIST.md)
- [CLIENT_HANDOFF_DAY6.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/submission/CLIENT_HANDOFF_DAY6.md)
- [ACCEPTANCE_LETTER.md](https://github.com/shellworlds/OPNBNK4/blob/main/deliverables/ACCEPTANCE_LETTER.md)
- [PROJECT_COMPLETION_REPORT.md](https://github.com/shellworlds/OPNBNK4/blob/main/PROJECT_COMPLETION_REPORT.md)

## Publish these wiki changes

Source lives in **`docs/wiki/`** on `main`. To update the live wiki: clone **`https://github.com/shellworlds/OPNBNK4.wiki.git`**, copy or merge from `docs/wiki/`, commit, **`git push`**.
