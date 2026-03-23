# Training Q&A log (Day 6)

Record questions from the client session and answers. Replace placeholders after the real workshop.

| # | Question | Answer |
|---|----------|--------|
| 1 | How do we add a new microservice? | Follow `docs/developer/onboarding.md`; register route on gateway; add CI matrix row. |
| 2 | What happens for payments over the fraud threshold? | Status **UNDER_REVIEW** until ops process (workflow TBD); **BLOCK** returns 403. |
| 3 | How do TPPs authenticate in production? | OAuth2 client credentials + Keycloak; replace demo headers. |
| 4 | Where are backups? | Azure automated backups for managed Postgres; see `disaster-recovery.md`. |
| 5 | How do we roll back a bad deploy? | `docs/operations/rollback-procedure.md`; images tagged v0.9.0 remain in GHCR. |

_Add rows after each training date._
