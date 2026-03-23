import { defineConfig } from "@playwright/test";

const baseURL = process.env.E2E_GATEWAY_URL || "http://localhost:8080";
const webURL = process.env.E2E_WEB_URL || "http://localhost:3000";

export default defineConfig({
  testDir: ".",
  timeout: 60_000,
  expect: { timeout: 15_000 },
  fullyParallel: false,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 1 : 0,
  reporter: process.env.CI ? "github" : "list",
  use: {
    baseURL,
    extraHTTPHeaders: {
      "X-Client-Id": "playwright-e2e",
      "X-Client-Channel": "automation",
      "X-Device-Id": "playwright-device-1",
    },
    trace: "on-first-retry",
  },
  projects: [
    { name: "api", testMatch: /api\/.*\.spec\.ts/ },
    {
      name: "web",
      testMatch: /web\/.*\.spec\.ts/,
      use: { baseURL: webURL, extraHTTPHeaders: {} },
    },
  ],
});
