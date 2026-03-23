# Hands-on training session script (facilitator)

**Duration:** ~45 minutes after the slide deck.  
**Prerequisites:** Staging URL, test Keycloak users, Postman collection imported.

## Setup (5 min)

- Confirm gateway health and Grafana login.
- Distribute staging credentials (separate from production).

## Exercises

1. **Web journey (10 min)**  
   - Sign in → dashboard → open one account → explain transaction statuses including **UNDER_REVIEW**.

2. **API with headers (10 min)**  
   - Postman: run Health + Accounts with `X-Client-Id` / `X-Client-Channel`.  
   - Optional: create a small payment and complete it.

3. **Open banking (10 min)**  
   - Walk through consent + AIS from smoke script or Postman (use demo headers in non-prod).

4. **Monitoring (10 min)**  
   - Grafana: show one latency or error panel; where to find pod logs (`kubectl` or portal).

5. **Wrap-up (5 min)**  
   - Log questions in `docs/training/faq-training.md`; open issues for gaps.

## Staging access

Provide:

- Base URL  
- Realm name and test user (no shared passwords in git — use password manager invite)
