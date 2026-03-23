# GDPR alignment (evidence pack — high level)

| Requirement | Implementation | Gap / next step |
|-------------|----------------|------------------|
| Lawful basis & consent | Open banking consent records in DB; Keycloak for identity | Customer consent UI polish |
| Data portability | `GET /api/user/export` (JSON snapshot) | Signed PDF / structured standard |
| Erasure | `POST /api/user/delete-request` (stub) | Anonymization jobs, cascade rules |
| Breach notification | Process outside repo | Define RACI |
| DPA with processors | Legal templates | Execute with cloud vendors |

This is **not** legal advice — validate with your DPO.
