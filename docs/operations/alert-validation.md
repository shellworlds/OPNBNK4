# Alert validation (Alertmanager)

## Goal

Prove critical alerts reach on-call (email, Slack, PagerDuty) before declaring go-live stable.

## Safe tests

- Add a temporary test rule or use DeadMansSwitch pattern.
- Staging: scale a deployment to zero briefly and confirm alert fires after `for:` duration.
- Restore and confirm resolved notification if configured.

## Record results

| Field | Value |
|-------|--------|
| Date | |
| Environment | |
| Alert name | |
| Channel | |
| Fire to notify latency | |

If Alertmanager is not wired, note **not configured** and open an issue from DAY6_DEDICATED_ISSUES.
