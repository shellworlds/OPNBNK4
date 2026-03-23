# FAQ

**Why do I see UNDER_REVIEW on a payment?**  
Large amounts (above internal EUR threshold) trigger fraud review rules.

**Why 400 on API calls?**  
The gateway may require `X-Client-Id` and `X-Client-Channel` headers.

**How do I export my data?**  
Operator-facing stub: `GET /api/user/export?customerId=...` (protect in production).

**Where is the developer portal?**  
TPP onboarding UI is partial; use demo headers and Postman patterns from smoke tests until the portal ships.
