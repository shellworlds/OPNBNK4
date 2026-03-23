# OPNBNK4 mobile (Expo)

Omni-channel React Native app: Keycloak PKCE (`expo-auth-session`), accounts dashboard, transactions, open-banking consent demo.

## Run (development)

```bash
npm install
npx expo start
```

Use **Continue without login** against local Docker Compose (gateway permits unauthenticated APIs in dev).

## Keycloak redirect

Add the URI printed on the login screen (e.g. `opnbnk4mobile://` or Expo dev URL) to the Keycloak client **Valid redirect URIs**.

## Production native OAuth

For bare / prebuild workflows, add **`react-native-app-auth`** and configure the issuer + redirect per platform (see [react-native-app-auth](https://github.com/FormidableLabs/react-native-app-auth)).

## Web static build (Docker)

```bash
npx expo export --platform web
```

Image serves `dist/` on port **3010** (see `Dockerfile`).
