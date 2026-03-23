import Keycloak from 'keycloak-js';

const url = process.env.REACT_APP_KEYCLOAK_URL;

function buildClient() {
  if (!url || !url.trim()) {
    return null;
  }
  return new Keycloak({
    url: url.replace(/\/$/, ''),
    realm: process.env.REACT_APP_KEYCLOAK_REALM || 'digital-banking',
    clientId: process.env.REACT_APP_KEYCLOAK_CLIENT_ID || 'web-portal',
  });
}

/** Singleton; null when Keycloak is not configured (local mock login). */
export const keycloak = buildClient();
