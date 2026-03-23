---
name: Day 3 JWT and smoke
about: Enable OAuth2 issuer on core services and tokenized smoke tests
title: "[Day 3] OAuth2 JWT on core services in Compose + smoke"
labels: security
---

## Goal

Set KEYCLOAK_ISSUER_URI on account, transaction, and openbanking services in docker-compose and extend smoke-e2e.py or CI to use a bearer token.

## Acceptance

- [ ] Authenticated API calls succeed in automated smoke
- [ ] Security docs updated
