# Digital Banking Platform

Microservices-based digital banking with open banking (PSD2-style) and omni-channel delivery (web, mobile APIs). The repo includes **working JPA-backed APIs** for accounts, ledger transactions, and open-banking consents, **Spring Cloud Gateway** with tests, a **React** web portal, Docker Compose, CI, and **automated tests** across services and the UI.

## 7-day roadmap (high level)

| Day | Focus |
|-----|--------|
| **1** | Repo scaffolding, architecture docs, core REST + JPA for account/transaction/consent, gateway + WireMock tests, React portal tests, Docker Compose, GitHub Actions CI |
| **2** | Domain models, PostgreSQL schemas, JPA entities, basic CRUD for accounts and transactions |
| **3** | Open banking APIs (consent, AIS/PIS stubs), OAuth2/OIDC integration design, API versioning |
| **4** | Event-driven flows (Kafka), idempotency, sagas or outbox for money movement |
| **5** | Security hardening (mTLS between services, secrets, policy), fraud hooks, audit logging |
| **6** | Frontend flows (onboarding, payments UI), mobile API BFF patterns, caching (Redis) |
| **7** | Kubernetes manifests refinement, Terraform for Azure, load tests, runbooks, demo hardening |

## Architecture overview

- **Backend**: Java 17+ / Spring Boot services (`account-service`, `transaction-service`, `openbanking-service`) behind **Spring Cloud Gateway**.
- **Data**: PostgreSQL for transactional data; **Redis** for cache/sessions; **Apache Kafka** for async integration.
- **Frontend**: React **web-portal**; **mobile-app** folder reserved for React Native.
- **Infrastructure**: Terraform (Azure), Kubernetes manifests, helper scripts under `infrastructure/`.
- **Docs**: Architecture, API contracts, and security notes under `docs/`.

See `docs/architecture/high-level-design.md` for C4-style diagrams and component boundaries.

## GitHub: wiki, project, issues, actions, insights

| Area | Link / location |
|------|------------------|
| **Releases** | [v1.0.1-day6 (Day 6 docs)](https://github.com/shellworlds/OPNBNK4/releases/tag/v1.0.1-day6) · [v1.0.0 (Day 5 production)](https://github.com/shellworlds/OPNBNK4/releases/tag/v1.0.0) · [v0.9.0 (Day 4 RC)](https://github.com/shellworlds/OPNBNK4/releases/tag/v0.9.0) · [v0.3.0-day3 (Day 3)](https://github.com/shellworlds/OPNBNK4/releases/tag/v0.3.0-day3) · [v0.2.0-day2](https://github.com/shellworlds/OPNBNK4/releases/tag/v0.2.0-day2) · [v0.1.0-day1](https://github.com/shellworlds/OPNBNK4/releases/tag/v0.1.0-day1) · [`CHANGELOG.md`](CHANGELOG.md) |
| **Client handoff (Day 6)** | **[docs/submission/CLIENT_HANDOFF_DAY6.md](docs/submission/CLIENT_HANDOFF_DAY6.md)** — **current** training, acceptance & post-deploy pack |
| **Final acceptance** | [FINAL_ACCEPTANCE_CHECKLIST.md](docs/submission/FINAL_ACCEPTANCE_CHECKLIST.md) · [ACCEPTANCE_LETTER.md](deliverables/ACCEPTANCE_LETTER.md) · [SUBMISSION_DAY6.md](SUBMISSION_DAY6.md) |
| **Client handoff (Day 5)** | [docs/submission/CLIENT_HANDOFF_DAY5.md](docs/submission/CLIENT_HANDOFF_DAY5.md) |
| **Project completion** | [PROJECT_COMPLETION_REPORT.md](PROJECT_COMPLETION_REPORT.md) · [GO_LIVE.md](GO_LIVE.md) |
| **Client handoff (Day 4)** | [docs/submission/CLIENT_HANDOFF_DAY4.md](docs/submission/CLIENT_HANDOFF_DAY4.md) |
| **Submission index (Day 4)** | [SUBMISSION_DAY4.md](SUBMISSION_DAY4.md) · [Verification results (Day 4)](docs/submission/VERIFICATION_RESULTS_DAY4.md) |
| **Client handoff (Day 3)** | [docs/submission/CLIENT_HANDOFF_DAY3.md](docs/submission/CLIENT_HANDOFF_DAY3.md) |
| **Submission index (Day 3)** | [SUBMISSION_DAY3.md](SUBMISSION_DAY3.md) · [Verification results](docs/submission/VERIFICATION_RESULTS_DAY3.md) |
| **Client handoff (Day 2)** | [docs/submission/CLIENT_HANDOFF_DAY2.md](docs/submission/CLIENT_HANDOFF_DAY2.md) |
| **Wiki** | **[Live wiki](https://github.com/shellworlds/OPNBNK4/wiki)** · **[Project complete (Days 1–6)](https://github.com/shellworlds/OPNBNK4/wiki/Project-Complete)** · source mirror [`docs/wiki/`](docs/wiki/) (clone `OPNBNK4.wiki.git` and push to update the wiki). |
| **Project (board)** | **[Projects](https://github.com/shellworlds/OPNBNK4/projects)** · **[OPNBNK4 Digital Banking Platform](https://github.com/users/shellworlds/projects/6)** — [PROJECT_COMPLETE_GITHUB.md](docs/github/PROJECT_COMPLETE_GITHUB.md) · [DAY6_DEDICATED_ISSUES.md](docs/github/DAY6_DEDICATED_ISSUES.md) · [DAY5_DEDICATED_ISSUES.md](docs/github/DAY5_DEDICATED_ISSUES.md) |
| **Issues** | [Issues](https://github.com/shellworlds/OPNBNK4/issues) · [New (templates)](https://github.com/shellworlds/OPNBNK4/issues/new/choose) · [Post-deployment defect](https://github.com/shellworlds/OPNBNK4/issues/new?template=post-deployment-defect.yml) · milestones **Day 1–6** |
| **Actions** | [Workflow runs](https://github.com/shellworlds/OPNBNK4/actions) — **`CI`** (incl. Playwright API E2E); **Day 1–5 verify** (manual; Day 5 also for post–Day 6 regression); **Security scan**; **Deploy AKS (tag)** |
| **Insights** | [Pulse](https://github.com/shellworlds/OPNBNK4/pulse) — activity and graphs (enable Dependabot / dependency graph under **Settings → Code security** if the client requires it) |

## Local development

### Prerequisites

- JDK 17+ (OpenJDK; **this machine**: OpenJDK **21**; CI uses Temurin **21**)
- Node.js 18+ and npm (**this machine**: Node **v20**)
- Docker and Docker Compose (**Docker 28.x**, **Compose v2** verified)
- Azure CLI, kubectl, minikube — present on **this machine** (Azure CLI 2.83, kubectl 1.35, minikube 1.37)
- **Terraform** and **kind** — not installed here; `sudo apt install terraform` / install [kind](https://kind.sigs.k8s.io/) when you need them (sudo was not available for unattended install)

### Run the stack

```bash
docker compose up --build
```

- **API Gateway**: http://localhost:8080  
- **Web portal (dev)**: http://localhost:3000  
- **PostgreSQL**: `localhost:5432` (user/password `bank`)  
- **Redis / Kafka / Zookeeper**: available **inside** the Compose network only (not published to the host by default, to avoid port clashes). Services can still use them at `redis:6379`, `kafka:29092`, etc.

Backend services use the `docker` Spring profile when started via Compose.

### Tests

```bash
# Each Java service
for d in backend/account-service backend/transaction-service backend/openbanking-service backend/api-gateway; do
  (cd "$d" && ./gradlew test)
done

# Web portal
cd frontend/web-portal && npm test -- --watchAll=false
```

- **account / transaction / openbanking**: JPA slice tests (`@DataJpaTest`), service unit tests (Mockito), MVC tests (`@WebMvcTest`), plus full-stack **`AccountFullStackIntegrationTest`** against H2.
- **api-gateway**: `RequestLoggingGlobalFilter` unit test and **WireMock**-backed route proxy test (`src/test/resources/application.yml` points routes at `127.0.0.1:8765`).
- **web-portal**: React Testing Library tests for login navigation and dashboard fetch/error handling.
- **PostgreSQL via Testcontainers**: `*PostgresIntegrationTest` in account, transaction, and openbanking services load **`infrastructure/fixtures/public-banking-samples.json`** (public IBAN-style examples, not real accounts).
- **E2E smoke**: `python3 infrastructure/scripts/smoke-e2e.py` (or `infrastructure/scripts/smoke-e2e.sh`) against a running Compose stack; same flow runs in CI job `e2e-open-data-smoke`.

See **`docs/day1-complete-report.md`** for a submission-ready Day 1 summary (with raw logs under **`docs/reports/day1-verification-2026-03-23/`**), and **`docs/verification.md`** for reproducible commands and counts.

### Git identity (local)

```bash
git config user.name "shellworlds"
git config user.email "rr@q-bit.space"
```

## Repository layout

```
digital-banking-platform/
├── backend/           # Microservices + gateway + shared-libs placeholder
├── frontend/          # web-portal (React), mobile-app (stub)
├── infrastructure/    # terraform, k8s, scripts
├── docs/              # architecture, api, security
├── .github/workflows/ # CI
└── docker-compose.yml
```

## License

Proprietary — internal use unless otherwise stated.
