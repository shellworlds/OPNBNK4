# Insights, dependency graph, and Dependabot

## Enabled in repository (as of Day 2 wrap-up)

- **Dependabot version updates** are configured in [`.github/dependabot.yml`](https://github.com/shellworlds/OPNBNK4/blob/main/.github/dependabot.yml) for:
  - Gradle (`account-service`, `transaction-service`, `openbanking-service`, `api-gateway`, `fraud-detection-service`, `core-simulator`, `notification-service`)
  - npm (`frontend/web-portal`, `frontend/mobile-app`)
  - GitHub Actions workflows

After merge to `default`, Dependabot opens **pull requests** for compatible updates. Review under **Pull requests** and the **Dependency graph** tab.

## Optional org/repo settings (GitHub UI)

If the **Dependency graph** does not show data yet, confirm under **Settings → Code security and analysis**:

- **Dependency graph** — enabled (read-only analysis).
- **Dependabot alerts** — optional, for vulnerable dependencies (separate from version-update PRs).
- **Dependabot security updates** — optional auto-PRs for CVEs.

## Insights → Pulse

[Pulse](https://github.com/shellworlds/OPNBNK4/pulse) is populated automatically from merges, issues, and releases; no repository file configures it.
