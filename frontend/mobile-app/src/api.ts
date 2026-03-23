import { GATEWAY_URL } from './config';

export type Account = {
  id: string;
  accountNumber: string;
  accountType: string;
  currency: string;
  balance: number | string;
};

export type Transaction = {
  id: string;
  amount: number | string;
  currency: string;
  type: string;
  status: string;
  reference?: string;
  description?: string;
};

export async function fetchJson<T>(path: string, token?: string | null): Promise<T> {
  const headers: Record<string, string> = { Accept: 'application/json' };
  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }
  const res = await fetch(`${GATEWAY_URL}${path}`, { headers });
  if (!res.ok) {
    const t = await res.text();
    throw new Error(`${res.status} ${t.slice(0, 200)}`);
  }
  return res.json() as Promise<T>;
}

export async function postJson<T>(path: string, body: unknown, token?: string | null): Promise<T> {
  const headers: Record<string, string> = {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  };
  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }
  const res = await fetch(`${GATEWAY_URL}${path}`, {
    method: 'POST',
    headers,
    body: JSON.stringify(body),
  });
  if (!res.ok) {
    const t = await res.text();
    throw new Error(`${res.status} ${t.slice(0, 200)}`);
  }
  if (res.status === 204) {
    return undefined as T;
  }
  return res.json() as Promise<T>;
}
