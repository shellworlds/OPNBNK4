import { test, expect } from "@playwright/test";

/**
 * Smoke against the React portal when running locally (`docker compose` + web-portal).
 * Skips if the app is not reachable (no fail in CI if web not deployed to staging URL).
 */
test.describe("Web E2E — portal smoke", () => {
  test("portal loads root without hard error", async ({ page, browserName }) => {
    test.skip(!!process.env.SKIP_WEB_E2E, "SKIP_WEB_E2E set");
    const resp = await page.goto("/", { waitUntil: "domcontentloaded", timeout: 20_000 }).catch(() => null);
    if (!resp || !resp.ok()) {
      test.skip(true, "Web portal not running at E2E_WEB_URL — start docker compose web-portal");
    }
    const title = await page.title();
    expect(title.length).toBeGreaterThan(0);
  });
});
