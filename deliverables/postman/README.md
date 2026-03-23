# Postman

- Import **`OPNBNK4-gateway.postman_collection.json`**.
- Duplicate folders for **transactions** and **open banking** from request bodies in `infrastructure/scripts/smoke-e2e.py` or extend this file in a follow-up PR.

Production: set **`baseUrl`**, add **Authorization: Bearer** when resource servers enforce JWT.
