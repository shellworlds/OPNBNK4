# GitHub: Project, Issues, Actions & Insights

← [Home](Home)

**Project status:** Delivery phases **Day 1–6** are complete in the repository (see **[Project complete — Days 1–6](Project-Complete)**). Use the **[project board checklist](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/PROJECT_COMPLETE_GITHUB.md)** to align GitHub **Projects**, **milestones**, and open **issues** with that state.

## Project board

The project is **linked to the repository**, so it appears under **[github.com/shellworlds/OPNBNK4/projects](https://github.com/shellworlds/OPNBNK4/projects)**.

**[OPNBNK4 Digital Banking Platform](https://github.com/users/shellworlds/projects/6)** (Project v2; same board as above)

- Use **Status** (Todo / In Progress / Done) so **Days 1–6** work items end in **Done**.
- Link **issues** and **PRs** from `shellworlds/OPNBNK4` via the issue sidebar **Projects** or the project UI **Add item**.
- **Dedicated issue lists (copy titles / backlog):**
  - [DAY2_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY2_DEDICATED_ISSUES.md) (historical)
  - [DAY3_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY3_DEDICATED_ISSUES.md)
  - [DAY4_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY4_DEDICATED_ISSUES.md)
  - [DAY5_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY5_DEDICATED_ISSUES.md)
  - [DAY6_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY6_DEDICATED_ISSUES.md)
- **Board closure steps:** [PROJECT_COMPLETE_GITHUB.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/PROJECT_COMPLETE_GITHUB.md)

## Issues

- **Milestones:** **Day 1** … **Day 6** (complete); optional **Day 7** for retrospective / BAU handoff only.
- **Labels:** `day-1` … `day-6`, `documentation`, `enhancement`, `bug`, `security`, `testing`, `open-banking`, `observability`, `operations`.
- **Templates:** **[Issue templates](https://github.com/shellworlds/OPNBNK4/issues/new/choose)** — from [`.github/ISSUE_TEMPLATE/`](https://github.com/shellworlds/OPNBNK4/tree/main/.github/ISSUE_TEMPLATE):
  - **Post-deployment defect** (YAML form): [issues/new?template=post-deployment-defect.yml](https://github.com/shellworlds/OPNBNK4/issues/new?template=post-deployment-defect.yml)
  - **day2_wiki_sync.md**, **day3_wiki_sync.md**, **day3_oauth2_smoke.md**, **day3_openapi.md**, **day4_webauthn_observability.md** (historical / optional follow-ups)

## Actions (CI/CD)

**[Actions tab](https://github.com/shellworlds/OPNBNK4/actions)** — workflows under [`.github/workflows/`](https://github.com/shellworlds/OPNBNK4/tree/main/.github/workflows):

| Workflow | Trigger | Purpose |
|----------|---------|---------|
| **CI** (`ci.yml`) | `push` / `pull_request` → `main` | Gradle test + `bootJar` per service; Postgres Testcontainers; npm; Docker; E2E Compose smoke; **Playwright API E2E** (`tests/e2e`) |
| **Day 1 verify** (`day1-verify.yml`) | Manual | Backend + frontend tests; optional E2E |
| **Day 2 verify** (`day2-verify.yml`) | Manual | Day 2 service set + `*Postgres*IntegrationTest`; optional E2E |
| **Day 3 verify** (`day3-verify.yml`) | Manual | Full backend set + `*Postgres*IntegrationTest`; optional E2E |
| **Day 4 verify** (`day4-verify.yml`) | Manual | Same gates; optional Compose + Playwright API; optional Trivy FS |
| **Day 5 verify** (`day5-verify.yml`) | Manual | Full sweep for go-live; use for **post–Day 6 regression** (no separate `day6-verify`) |
| **Security scan** (`security-scan.yml`) | Manual | Trivy filesystem (SARIF) + gateway image scan |
| **Deploy AKS (tag)** (`deploy-aks.yml`) | **Version tag** push | Build/push images to **GHCR**; Helm upgrade gated (`if: false` until Azure secrets) |
| **SonarCloud (optional)** (`sonar-quality.yml`) | Stub | Enable when `SONAR_TOKEN` is configured |

## Insights

**[Pulse / Insights](https://github.com/shellworlds/OPNBNK4/pulse)** — activity, contributors, and (if enabled) traffic.

**Dependency graph & Dependabot:** [`.github/dependabot.yml`](https://github.com/shellworlds/OPNBNK4/blob/main/.github/dependabot.yml) enables **version update PRs** for Gradle, npm, and Actions. See [INSIGHTS_DEPENDABOT.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/INSIGHTS_DEPENDABOT.md) for UI settings (dependency graph, optional alerts).

## Wiki (this site)

Live wiki: **[github.com/shellworlds/OPNBNK4/wiki](https://github.com/shellworlds/OPNBNK4/wiki)**. Source Markdown is mirrored under **`docs/wiki/`** in the main repo; clone **`https://github.com/shellworlds/OPNBNK4.wiki.git`**, copy or merge from `docs/wiki/`, then **`git push`** to publish updates.

**Start here for “everything shipped”:** [Project complete — Days 1–6](Project-Complete).
