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
| **Releases** | [v0.1.0-day1 (Day 1)](https://github.com/shellworlds/OPNBNK4/releases/tag/v0.1.0-day1) · [`CHANGELOG.md`](CHANGELOG.md) |
| **Wiki (source in repo)** | [`docs/wiki/Home.md`](docs/wiki/Home.md) — copy into the GitHub **Wiki** after you create the first page in the UI (remote wiki git is provisioned then). |
| **Project (board)** | [OPNBNK4 Digital Banking Platform](https://github.com/users/shellworlds/projects/6) — Day 1 items **Done**, backlog **Todo**. |
| **Issues** | [shellworlds/OPNBNK4/issues](https://github.com/shellworlds/OPNBNK4/issues) · milestone **Day 1** |
| **Actions** | [Workflow runs](https://github.com/shellworlds/OPNBNK4/actions) — `CI` on push/PR; **Day 1 verify (manual)** for on-demand test reports |
| **Insights** | [Pulse / contributors](https://github.com/shellworlds/OPNBNK4/pulse) — activity and graphs (GitHub-managed; enable Dependabot / dependency graph in **Settings** if required) |

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

See **`docs/verification.md`** for last recorded test counts and commands.

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
