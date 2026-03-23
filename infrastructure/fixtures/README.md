# Fixtures

- **`public-banking-samples.json`** — IBAN-style examples widely used in educational material and ISO 13616 discussions, plus illustrative transaction/consent rows for smoke tests. **Not real customer data.**

Gradle test modules include this directory on the test classpath so `AccountPostgresIntegrationTest` (and siblings) can read `/public-banking-samples.json`.
