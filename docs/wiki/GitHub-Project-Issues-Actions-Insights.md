# GitHub: Project, Issues, Actions & Insights

← [Home](Home)

## Project board

The project is **linked to the repository**, so it appears under **[github.com/shellworlds/OPNBNK4/projects](https://github.com/shellworlds/OPNBNK4/projects)**.

**[OPNBNK4 Digital Banking Platform](https://github.com/users/shellworlds/projects/6)** (Project v2; same board as above)

- Use **Status** (Todo / In Progress / Done) to track delivery and backlog.
- Link **issues** and **PRs** from `shellworlds/OPNBNK4` via the issue sidebar **Projects** or the project UI **Add item**.
- **Day 3 / Day 4:** Add issues from **[DAY3_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY3_DEDICATED_ISSUES.md)** (Day 3 closure + historical Day 4 backlog) and **[DAY4_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY4_DEDICATED_ISSUES.md)** (Day 4 closure + Day 5 backlog). Use **Milestones** `Day 3`, `Day 4`, and `Day 5`.
- **Day 2 (historical):** **[DAY2_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY2_DEDICATED_ISSUES.md)**

## Issues

- **Milestones:** **Day 1** (historical), **Day 2** (historical), **Day 3** (closure + handoff), **Day 4** (closure + handoff), **Day 5** (backlog).
- **Labels:** `day-1`, `day-2`, `day-3`, `day-4`, `documentation`, `enhancement`, `bug`, `security`, `testing`, `open-banking`, `observability`.
- **Templates:** **[Issue templates](https://github.com/shellworlds/OPNBNK4/issues/new/choose)** — Day 2 wiki sync, Day 3 wiki sync, Day 4 WebAuthn/observability, Day 3 JWT/smoke, Day 3 OpenAPI (`.github/ISSUE_TEMPLATE/`).

## Actions (CI/CD)

**[Actions tab](https://github.com/shellworlds/OPNBNK4/actions)**

| Workflow | Purpose |
|----------|---------|
| **CI** (`ci.yml`) | Gradle test + bootJar per service (incl. fraud, core-simulator, notification); **Postgres Testcontainers** job; npm test + build; Docker builds; E2E Compose smoke; **Playwright API E2E** (`tests/e2e`) on `main` / PRs |
| **Day 1 verify** (`day1-verify.yml`) | Manual **Run workflow** — backend + frontend tests; optional E2E |
| **Day 2 verify** (`day2-verify.yml`) | Manual **Run workflow** — Day 2 service set **plus** `*Postgres*IntegrationTest`; optional E2E |
| **Day 3 verify** (`day3-verify.yml`) | Manual **Run workflow** — full current backend set **plus** `*Postgres*IntegrationTest`; optional E2E |
| **Day 4 verify** (`day4-verify.yml`) | Manual **Run workflow** — same backend/npm gates; optional Compose + Playwright API; optional Trivy FS |
| **Security scan** (`security-scan.yml`) | Manual — Trivy filesystem (SARIF) + gateway image scan |
| **Deploy AKS (tag)** (`deploy-aks.yml`) | On **version tag** push — build/push all images to **GHCR**; Helm upgrade job gated (`if: false` until Azure secrets) |
| **SonarCloud (optional)** (`sonar-quality.yml`) | Disabled stub — enable when `SONAR_TOKEN` is configured |

## Insights

**[Pulse / Insights](https://github.com/shellworlds/OPNBNK4/pulse)** — populated by GitHub from activity, contributors, and (if enabled) traffic.

**Dependency graph & Dependabot:** [`.github/dependabot.yml`](https://github.com/shellworlds/OPNBNK4/blob/main/.github/dependabot.yml) enables **version update PRs** for Gradle, npm, and Actions. See [INSIGHTS_DEPENDABOT.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/INSIGHTS_DEPENDABOT.md) for UI settings (dependency graph, optional alerts).

## Wiki (this site)

Live wiki: **[github.com/shellworlds/OPNBNK4/wiki](https://github.com/shellworlds/OPNBNK4/wiki)**. Source Markdown is mirrored under **`docs/wiki/`** in the main repo; clone **`https://github.com/shellworlds/OPNBNK4.wiki.git`**, copy or merge from `docs/wiki/`, then **`git push`** to publish updates.
