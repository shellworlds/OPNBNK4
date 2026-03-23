# GDPR — evidence checklist

| Control | Evidence | Location in platform |
|---------|----------|----------------------|
| Consent records | Sample query result (redacted) or screenshot | `openbanking-service` consent persistence |
| Data portability | Successful **GET /api/user/export** | `account-service` `UserDataController` |
| Erasure request | **POST /api/user/delete-request** + ticket ID | Same controller (stub workflow) |
| Retention | Policy doc (external) | _client DMS_ |
| Subprocessors | List (Azure, etc.) | _client legal_ |

**Note:** Full anonymization pipelines are not implemented in-repo; track in product backlog.
