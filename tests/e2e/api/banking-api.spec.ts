import { test, expect } from "@playwright/test";

const channelHeaders = {
  "X-Client-Id": "playwright-e2e",
  "X-Client-Channel": "automation",
  "X-Device-Id": "playwright-device-1",
};

test.describe("API E2E — gateway", () => {
  test("account CRUD flow and transaction with Kafka path", async ({ request }) => {
    const suffix = Date.now();
    const customerId = `e2e-cust-${suffix}`;
    const iban = `GB82WEST${String(suffix).padStart(14, "0").slice(-14)}`;

    const createAcc = await request.post("/api/accounts", {
      headers: { ...channelHeaders, "Content-Type": "application/json" },
      data: {
        customerId,
        accountNumber: iban,
        accountType: "CHECKING",
        currency: "GBP",
        initialBalance: 500,
      },
    });
    expect(createAcc.ok()).toBeTruthy();
    const acc = await createAcc.json();
    const accountId = acc.id as string;

    const getOne = await request.get(`/api/accounts/${accountId}`, { headers: channelHeaders });
    expect(getOne.ok()).toBeTruthy();
    const detail = await getOne.json();
    expect(detail.customerId).toBe(customerId);

    const txCreate = await request.post("/api/transactions", {
      headers: { ...channelHeaders, "Content-Type": "application/json" },
      data: {
        accountId,
        amount: 10,
        type: "DEBIT",
        currency: "GBP",
        reference: `e2e-${suffix}`,
        description: "Playwright API E2E",
      },
    });
    expect(txCreate.status()).toBe(201);
    const tx = await txCreate.json();
    expect(tx.status).toBe("PENDING");

    const complete = await request.post(`/api/transactions/${tx.id}/complete`, {
      headers: channelHeaders,
    });
    expect(complete.ok()).toBeTruthy();
    const done = await complete.json();
    expect(done.status).toBe("COMPLETED");

    const listTx = await request.get(`/api/transactions/account/${accountId}`, { headers: channelHeaders });
    expect(listTx.ok()).toBeTruthy();
    const txs = await listTx.json();
    expect(Array.isArray(txs)).toBeTruthy();
    expect(txs.length).toBeGreaterThanOrEqual(1);
  });

  test("open banking AIS + consent revoke + PIS", async ({ request }) => {
    const suffix = Date.now();
    const customerId = `e2e-ob-${suffix}`;
    const iban = `DE8937040044053201${String(suffix % 10000).padStart(4, "0")}`;

    const createAcc = await request.post("/api/accounts", {
      headers: { ...channelHeaders, "Content-Type": "application/json" },
      data: {
        customerId,
        accountNumber: iban,
        accountType: "CHECKING",
        currency: "EUR",
        initialBalance: 1000,
      },
    });
    expect(createAcc.ok()).toBeTruthy();
    const acc = await createAcc.json();
    const accountId = acc.id as string;

    const consent = await request.post("/openbanking/consents", {
      headers: { ...channelHeaders, "Content-Type": "application/json" },
      data: {
        tppId: "tpp-aisp-demo",
        scopes: ["accounts:read", "transactions:read", "payments:write"],
        customerId,
      },
    });
    expect(consent.status()).toBe(201);
    const c = await consent.json();
    const consentId = c.consentId as string;

    const aisHeaders = {
      ...channelHeaders,
      "X-Demo-Tpp-Id": "tpp-aisp-demo",
      "X-Demo-Customer-Id": customerId,
    };
    const accounts = await request.get("/openbanking/accounts", { headers: aisHeaders });
    expect(accounts.ok()).toBeTruthy();
    const accList = await accounts.json();
    expect(Array.isArray(accList)).toBeTruthy();

    const pay = await request.post("/openbanking/payments", {
      headers: { ...aisHeaders, "Content-Type": "application/json" },
      data: {
        accountId,
        amount: 5,
        type: "DEBIT",
        currency: "EUR",
        reference: `pis-e2e-${suffix}`,
      },
    });
    expect(pay.ok()).toBeTruthy();
    const payBody = await pay.json();
    expect(payBody.status).toBe("COMPLETED");

    const revoke = await request.delete(`/openbanking/consents/${consentId}`, { headers: channelHeaders });
    expect(revoke.status()).toBe(204);
  });

  test("channel validation rejects missing headers when enabled", async ({ request }) => {
    const r = await request.get("/api/accounts", {
      headers: { Accept: "application/json" },
    });
    expect(r.status()).toBe(400);
  });

  test("user data export stub", async ({ request }) => {
    const r = await request.get("/api/user/export?customerId=open-sample-uk-001", { headers: channelHeaders });
    expect(r.ok()).toBeTruthy();
    const body = await r.json();
    expect(body).toHaveProperty("customerId");
    expect(body).toHaveProperty("accounts");
  });
});
