# Digital Banking Platform

Microservices-based digital banking with open banking (PSD2-style) and omni-channel delivery (web, mobile APIs). The repo includes **working JPA-backed APIs** for accounts, ledger transactions, and open-banking consents, **Spring Cloud Gateway** with tests, a **React** web portal, Docker Compose, CI, and **automated tests** across services and the UI.

## 7-day roadmap (high level)

| Day | Focus |
|-----|--------|
| **1** | Repo scaffolding, architecture docs, Spring Boot / React / Gateway skeletons, Docker Compose, GitHub Actions CI |
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
- **Redis**: `localhost:6379`  
- **Kafka**: `localhost:9092` (internal listener for services on Docker network)

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
