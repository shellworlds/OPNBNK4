# API reference (index)

Aggregated **OpenAPI** export is not yet published from every service. Use the following entry points:

| Area | Base path (via gateway) | Notes |
|------|-------------------------|--------|
| Accounts | `/api/accounts` | CRUD-style REST |
| Transactions | `/api/transactions` | Create, complete (`POST .../complete`), list by account |
| User data (GDPR stubs) | `/api/user/export`, `/api/user/delete-request` | Query param `customerId` |
| Open banking | `/openbanking/*` | Consents, AIS, PIS — demo headers in dev |
| Health | `/actuator/health` | Per service; gateway exposes actuator |

**HTML / Swagger:** Enable `springdoc-openapi` on each service and aggregate behind the gateway in a future sprint, or import Postman collections from `infrastructure/scripts/smoke-e2e.py` request shapes.

**Machine-readable stubs:** Place exported `openapi.json` files under `deliverables/api-specs/` when generated (this repo ships `README.md` there as a placeholder).
