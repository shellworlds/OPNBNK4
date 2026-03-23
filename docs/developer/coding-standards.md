# Coding standards

- **Java:** Spring Boot 3.3, Java 21, four-space indent, JUnit 5 + AssertJ in tests. Prefer constructor injection; avoid `TODO` in committed code (track in issues).
- **Formatting:** IDE Google Java Style or IntelliJ defaults; **Spotless** may be added in CI later — not enforced in this repo yet.
- **React:** Functional components, hooks; Prettier via Create React App defaults (`npm run build` must pass).
- **APIs:** Problem Details (`RFC7807`) where practical for errors; gateway forwards correlation IDs when added.
- **Security:** No secrets in source; use env / Key Vault.

## Git

- Conventional commits encouraged (`feat:`, `fix:`, `docs:`).
- PRs require green CI before merge to `main`.
