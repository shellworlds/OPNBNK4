# GitHub: Project, Issues, Actions & Insights

← [Home](Home)

## Project board

The project is **linked to the repository**, so it appears under **[github.com/shellworlds/OPNBNK4/projects](https://github.com/shellworlds/OPNBNK4/projects)**.

**[OPNBNK4 Digital Banking Platform](https://github.com/users/shellworlds/projects/6)** (Project v2; same board as above)

- Use **Status** (Todo / In Progress / Done) to track delivery and backlog.
- Link **issues** and **PRs** from `shellworlds/OPNBNK4` via the issue sidebar **Projects** or the project UI **Add item**.
- **Day 2:** Add issues from **[docs/github/DAY2_DEDICATED_ISSUES.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/DAY2_DEDICATED_ISSUES.md)** (closure + Day 3 backlog). Use **Milestone** `Day 2` for acceptance items.

## Issues

- **Milestones:** **Day 1** (historical), **Day 2** (closure + handoff), **Day 3** (backlog).
- **Labels:** `day-1`, `day-2`, `day-3`, `documentation`, `enhancement`, `bug`, `security`, `testing`, `open-banking`.
- **Templates:** Repository **[Issue templates](https://github.com/shellworlds/OPNBNK4/issues/new/choose)** — Day 2 wiki sync, Day 3 JWT/smoke, Day 3 OpenAPI (see `.github/ISSUE_TEMPLATE/`).

## Actions (CI/CD)

**[Actions tab](https://github.com/shellworlds/OPNBNK4/actions)**

| Workflow | Purpose |
|----------|---------|
| **CI** (`ci.yml`) | Gradle test + bootJar per service; **Postgres Testcontainers** job; npm test + build; Docker builds; E2E Compose smoke on `main` / PRs |
| **Day 1 verify** (`day1-verify.yml`) | Manual **Run workflow** — backend + frontend tests; optional E2E |
| **Day 2 verify** (`day2-verify.yml`) | Manual **Run workflow** — all of the above **plus** `*Postgres*IntegrationTest`; optional E2E |

## Insights

**[Pulse / Insights](https://github.com/shellworlds/OPNBNK4/pulse)** — populated by GitHub from activity, contributors, and (if enabled) traffic.

**Dependency graph & Dependabot:** [`.github/dependabot.yml`](https://github.com/shellworlds/OPNBNK4/blob/main/.github/dependabot.yml) enables **version update PRs** for Gradle, npm, and Actions. See [INSIGHTS_DEPENDABOT.md](https://github.com/shellworlds/OPNBNK4/blob/main/docs/github/INSIGHTS_DEPENDABOT.md) for UI settings (dependency graph, optional alerts).

## Wiki (this site)

Live wiki: **[github.com/shellworlds/OPNBNK4/wiki](https://github.com/shellworlds/OPNBNK4/wiki)**. Source Markdown is mirrored under **`docs/wiki/`** in the main repo; clone **`https://github.com/shellworlds/OPNBNK4.wiki.git`**, copy or merge from `docs/wiki/`, then **`git push`** to publish updates.
