# GitHub: Project, Issues, Actions & Insights

## Project board

**[OPNBNK4 Digital Banking Platform](https://github.com/users/shellworlds/projects/6)** (user-scoped Project v2)

- Use **Status** (Todo / In Progress / Done) to track delivery and backlog.
- Link **issues** and **PRs** from `shellworlds/OPNBNK4` via the issue sidebar **Projects** or the project UI **Add item**.

## Issues

- **Milestones:** Use a **Day 1** milestone for client-facing closure.
- **Labels:** `day-1`, `documentation`, `enhancement`, `bug` (created from initial setup issues).

## Actions (CI/CD)

**[Actions tab](https://github.com/shellworlds/OPNBNK4/actions)**

| Workflow | Purpose |
|----------|---------|
| **CI** (`ci.yml`) | Gradle test + bootJar; npm test + build; Docker builds; E2E smoke on `main` / PRs |
| **Day 1 verify** (`day1-verify.yml`) | Manual **Run workflow** to re-run backend + frontend tests |

## Insights

**[Pulse / Insights](https://github.com/shellworlds/OPNBNK4/pulse)** — populated by GitHub from activity, contributors, and (if enabled) traffic. Enable **Dependency graph** / **Dependabot** under **Settings → Code security** if the client requires it.

## Wiki (GitHub)

If **Wiki** is enabled but empty: open **Wiki → Create first page**, or clone `https://github.com/shellworlds/OPNBNK4.wiki.git` after the first page exists, then sync content from `docs/wiki/` in this repository.
