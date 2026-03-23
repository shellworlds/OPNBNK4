# Client training deck — OPNBNK4 (Markdown outline)

**Total ~60 minutes** — adjust depth per audience. Speaker notes in _italics_.

---

## Slide 1 — Title
- Digital Banking Platform — handover & operations
- Version: v1.0.0 · Repo: github.com/shellworlds/OPNBNK4

---

## Section A — Architecture overview (~10 min)

- **Slide 2:** Users → API Gateway → services (account, transaction, open banking, fraud, notifications).
- **Slide 3:** Data: PostgreSQL, Redis, Kafka; identity: Keycloak.
- **Slide 4:** Diagram: point to `docs/architecture/final-architecture.md` and C4 in `high-level-design.md`.
- _Demo: Grafana targets “up” in Compose._

---

## Section B — Admin & monitoring (~15 min)

- **Slide 5:** Grafana: latency, errors, JVM, (future) Kafka lag.
- **Slide 6:** Logs: Azure Log Analytics vs `kubectl logs`.
- **Slide 7:** Fraud: rule engine, **UNDER_REVIEW** vs **BLOCK**; where to see alerts.
- **Slide 8:** Keycloak: realm, users, lockout/brute-force (configure per policy).
- _Demo: open Grafana; show one healthy panel._

---

## Section C — Developer onboarding (~15 min)

- **Slide 9:** Clone, JDK 21, `docker compose up`, `./gradlew test`.
- **Slide 10:** Adding a route: gateway YAML / controller pattern; channel headers.
- **Slide 11:** CI: PR → Gradle, npm, Docker, Playwright API.
- **Slide 12:** Links: `docs/developer/onboarding.md`, `ci-cd.md`.

---

## Section D — TPP / open banking (~10 min)

- **Slide 13:** Consent + AIS/PIS paths; demo headers in dev vs OAuth2 in prod.
- **Slide 14:** Postman collection in `deliverables/postman/`.
- **Slide 15:** Rate limits on `/openbanking/**` (Redis).

---

## Section E — Incident response (~10 min)

- **Slide 16:** Severity levels; when to rollback (`docs/operations/rollback-procedure.md`).
- **Slide 17:** File **Post-deployment defect** issue template.
- **Slide 18:** Contacts — runbook Support table (fill before session).

---

## Q&A
- Capture in `docs/training/faq-training.md`.
