import axios from 'axios';
import { keycloak } from '../auth/keycloakClient';

const gatewayBase = process.env.REACT_APP_GATEWAY_URL?.replace(/\/$/, '') || 'http://localhost:8080';

export const api = axios.create({ baseURL: gatewayBase });

api.interceptors.request.use((config) => {
  if (keycloak?.token) {
    const next = { ...config };
    next.headers = { ...config.headers, Authorization: `Bearer ${keycloak.token}` };
    return next;
  }
  return config;
});

api.interceptors.response.use(
  (r) => r,
  async (error) => {
    const original = error.config;
    if (!keycloak || !original || original._retry || error.response?.status !== 401) {
      throw error;
    }
    original._retry = true;
    await keycloak.updateToken(30);
    original.headers.Authorization = `Bearer ${keycloak.token}`;
    return api.request(original);
  }
);

export async function getJson(path) {
  const { data } = await api.get(path);
  return data;
}
