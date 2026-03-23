#!/usr/bin/env python3
"""
End-to-end smoke against the API gateway using public-domain IBAN-style samples
from infrastructure/fixtures/public-banking-samples.json (educational examples only).
"""
from __future__ import annotations

import argparse
import json
import sys
import time
import urllib.error
import urllib.request
from pathlib import Path


def http_json(method: str, url: str, body: object | None = None, timeout: float = 30):
    data = None
    headers = {"Accept": "application/json"}
    if body is not None:
        payload = json.dumps(body).encode("utf-8")
        data = payload
        headers["Content-Type"] = "application/json"
    req = urllib.request.Request(url, data=data, headers=headers, method=method)
    with urllib.request.urlopen(req, timeout=timeout) as resp:
        raw = resp.read().decode("utf-8")
        if not raw:
            return None
        return json.loads(raw)


def wait_gateway(gateway: str, attempts: int = 90, delay: float = 2.0) -> None:
    health = f"{gateway.rstrip('/')}/actuator/health"
    last_err: Exception | None = None
    for _ in range(attempts):
        try:
            with urllib.request.urlopen(health, timeout=5) as r:
                if r.status == 200:
                    return
        except Exception as e:
            last_err = e
        time.sleep(delay)
    raise SystemExit(f"Gateway not healthy at {health}: {last_err}")


def wait_backend_routes(gateway: str, attempts: int = 90, delay: float = 2.0) -> None:
    """Wait until routed GET /api/accounts succeeds (gateways + downstream services up)."""
    url = f"{gateway.rstrip('/')}/api/accounts"
    last_err: Exception | None = None
    for _ in range(attempts):
        try:
            req = urllib.request.Request(url, method="GET")
            with urllib.request.urlopen(req, timeout=10) as r:
                if r.status == 200:
                    return
        except Exception as e:
            last_err = e
        time.sleep(delay)
    raise SystemExit(f"Backend routes not ready at {url}: {last_err}")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--gateway", default="http://localhost:8080", help="API gateway base URL")
    parser.add_argument(
        "--fixture",
        default=None,
        help="Path to public-banking-samples.json (default: repo infrastructure/fixtures/...)",
    )
    args = parser.parse_args()
    gateway = args.gateway.rstrip("/")

    repo_root = Path(__file__).resolve().parents[2]
    fixture_path = Path(args.fixture) if args.fixture else repo_root / "infrastructure" / "fixtures" / "public-banking-samples.json"
    data = json.loads(fixture_path.read_text(encoding="utf-8"))

    print("Waiting for gateway health…")
    wait_gateway(gateway)
    print("Waiting for routed APIs…")
    wait_backend_routes(gateway)
    print("Stack OK.\n")

    results: dict = {"gateway": gateway, "accounts_created": [], "transactions": [], "consents": []}

    for acc in data["accounts"]:
        payload = {
            "iban": acc["iban"],
            "currency": acc["currency"],
            "customerId": acc["customerId"],
            "initialBalance": acc["initialBalance"],
        }
        created = http_json("POST", f"{gateway}/api/accounts", payload)
        results["accounts_created"].append({"request": payload, "response": created})
        print("POST /api/accounts", acc["iban"], "->", created.get("id") if created else None)

    listed = http_json("GET", f"{gateway}/api/accounts")
    if not listed:
        raise SystemExit("No accounts returned from GET /api/accounts")
    first_id = listed[0]["id"]
    print("\nUsing account id for transactions:", first_id)

    for tx in data["transactions"]:
        payload = {
            "accountId": first_id,
            "amount": tx["amount"],
            "currency": tx["currency"],
            "reference": tx["reference"],
        }
        created = http_json("POST", f"{gateway}/api/transactions", payload)
        results["transactions"].append({"request": payload, "response": created})
        print("POST /api/transactions", tx["reference"][:40], "->", created.get("id") if created else None)

    tx_list = http_json("GET", f"{gateway}/api/transactions?accountId={first_id}")
    print("GET /api/transactions count:", len(tx_list) if tx_list else 0)

    for c in data["consents"]:
        payload = {"tppId": c["tppId"], "scopes": c["scopes"], "customerId": c["customerId"]}
        if c.get("validUntil") is not None:
            payload["validUntil"] = c["validUntil"]
        created = http_json("POST", f"{gateway}/api/openbanking/consents", payload)
        results["consents"].append({"request": payload, "response": created})
        print("POST /api/openbanking/consents", c["tppId"], "->", created.get("consentId") if created else None)

    consent_list = http_json("GET", f"{gateway}/api/openbanking/consents")
    print("GET /api/openbanking/consents count:", len(consent_list) if consent_list else 0)

    print("\n=== Summary (open-sample data) ===")
    print(json.dumps(results, indent=2))
    print("\nSmoke completed successfully.")
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except urllib.error.HTTPError as e:
        body = e.read().decode("utf-8", errors="replace")
        print(f"HTTP {e.code} {e.reason}: {body}", file=sys.stderr)
        raise SystemExit(1)
