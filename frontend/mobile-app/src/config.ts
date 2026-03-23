const strip = (u: string | undefined, fallback: string) =>
  (u ?? fallback).replace(/\/$/, '');

/** API gateway (same host as web portal in dev). */
export const GATEWAY_URL = strip(process.env.EXPO_PUBLIC_GATEWAY_URL, 'http://localhost:8080');

/** Keycloak realm issuer URL, e.g. http://localhost:8180/realms/digital-banking */
export const KEYCLOAK_ISSUER = strip(
  process.env.EXPO_PUBLIC_KEYCLOAK_ISSUER,
  'http://localhost:8180/realms/digital-banking'
);

export const KEYCLOAK_CLIENT_ID = process.env.EXPO_PUBLIC_KEYCLOAK_CLIENT_ID ?? 'web-portal';

export const MOCK_CUSTOMER_ID = process.env.EXPO_PUBLIC_MOCK_CUSTOMER_ID ?? 'open-sample-uk-001';
