# GitHub: project board & issues — Days 1–6 complete

Use this checklist so **Project #6**, **milestones**, and **issues** match the completed delivery documented in the repo and wiki **[Project complete — Days 1–6](https://github.com/shellworlds/OPNBNK4/wiki/Project-Complete)**.

## Project board

- **Open:** [OPNBNK4 Digital Banking Platform](https://github.com/users/shellworlds/projects/6) (also under [repository Projects](https://github.com/shellworlds/OPNBNK4/projects)).
- Move every item that corresponds to **Days 1–6** scope to **Done** (or your closed column).
- Leave **Day 7**-only items (retrospective, BAU handoff) in **Todo** / **In Progress** if you are still running Day 7.

## Milestones (recommended)

Create or verify milestones **Day 1** … **Day 6** and **close** each when work is finished. Optional: **Day 7** for closure-only tasks.

| Milestone | Typical closure |
|-----------|-----------------|
| Day 1 | Scaffolding, first CI, initial APIs |
| Day 2 | PostgreSQL, core CRUD |
| Day 3 | Open banking, gateway hardening |
| Day 4 | E2E, observability, security scans, RC `v0.9.0` |
| Day 5 | UAT pack, go-live docs, `v1.0.0` |
| Day 6 | Post-deploy docs, training pack, acceptance templates, `v1.0.1-day6` |

## Issue titles you can create (if not already tracked)

From [DAY6_DEDICATED_ISSUES.md](./DAY6_DEDICATED_ISSUES.md):

- **[Day 6] Post-deploy validation signed** — link `docs/operations/post-deployment-validation.md` or ticket.
- **[Day 6] Alert test** — link `docs/operations/alert-validation.md` or defer note.
- **[Day 6] Training delivered** — external recording link + `docs/training/faq-training.md`.

From [DAY5_DEDICATED_ISSUES.md](./DAY5_DEDICATED_ISSUES.md) (close when satisfied or explicitly waived):

- **[Day 5] UAT sign-off PDF**
- **[Day 5] Staging load test numbers**
- **[Day 5] Record demo.mp4** (client share, not git)

**Post-release defects:** [Post-deployment defect](https://github.com/shellworlds/OPNBNK4/issues/new?template=post-deployment-defect.yml).

## Actions after board cleanup

1. Run **[CI](https://github.com/shellworlds/OPNBNK4/actions/workflows/ci.yml)** on `main` (should be green after last push).
2. Optionally run **Day 5 verify** for a full manual regression: [day5-verify.yml](https://github.com/shellworlds/OPNBNK4/actions/workflows/day5-verify.yml).
3. Confirm **[Releases](https://github.com/shellworlds/OPNBNK4/releases)** lists at least **v1.0.0** and **v1.0.1-day6** (and earlier tags as needed).
