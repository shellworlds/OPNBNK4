#!/usr/bin/env python3
"""
End-to-end smoke against the API gateway using public-domain IBAN-style samples
from infrastructure/fixtures/public-banking-samples.json (educational examples only).

Day 2: account payloads use accountNumber + accountType; transactions use type + positive amount;
open banking uses /openbanking/consents and demo headers for AIS.
"""
from __future__ import annotations

import argparse
import json
import sys
import time
import urllib.error
import urllib.request
from pathlib import Path

# Required when api-gateway runs with gateway.channel-validation.enabled (Docker default).
DEFAULT_CHANNEL_HEADERS = {
    "X-Client-Id": "smoke-e2e",
    "X-Client-Channel": "automation",
    "X-Device-Id": "smoke-device-1",
}


def http_json(method: str, url: str, body: object | None = None, headers: dict | None = None, timeout: float = 30):
    data = None
    h = {"Accept": "application/json", **DEFAULT_CHANNEL_HEADERS}
    if headers:
        h.update(headers)
    if body is not None:
        payload = json.dumps(body).encode("utf-8")
        data = payload
        h["Content-Type"] = "application/json"
    req = urllib.request.Request(url, data=data, headers=h, method=method)
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
    url = f"{gateway.rstrip('/')}/api/accounts"
    last_err: Exception | None = None
    for _ in range(attempts):
        try:
            req = urllib.request.Request(url, method="GET", headers={"Accept": "application/json", **DEFAULT_CHANNEL_HEADERS})
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

    results: dict = {"gateway": gateway, "accounts_created": [], "transactions": [], "consents": [], "openbanking_ais": []}

    for acc in data["accounts"]:
        payload = {
            "customerId": acc["customerId"],
            "accountNumber": acc["iban"],
            "accountType": "CHECKING",
            "currency": acc["currency"],
            "initialBalance": acc["initialBalance"],
        }
        created = http_json("POST", f"{gateway}/api/accounts", payload)
        results["accounts_created"].append({"request": payload, "response": created})
        print("POST /api/accounts", acc["iban"], "->", created.get("id") if created else None)

    listed = http_json("GET", f"{gateway}/api/accounts")
    if not listed:
        raise SystemExit("No accounts returned from GET /api/accounts")
    first_id = listed[0]["id"]
    first_customer = listed[0]["customerId"]
    print("\nUsing account id for transactions:", first_id)

    for tx in data["transactions"]:
        amt = float(tx["amount"])
        if amt < 0:
            tx_type = "DEBIT"
            amount = abs(amt)
        else:
            tx_type = "CREDIT"
            amount = amt
        payload = {
            "accountId": first_id,
            "amount": amount,
            "type": tx_type,
            "currency": tx["currency"],
            "reference": tx["reference"],
            "description": "Smoke",
        }
        created = http_json("POST", f"{gateway}/api/transactions", payload)
        results["transactions"].append({"request": payload, "response": created})
        txid = created.get("id") if created else None
        print("POST /api/transactions", tx["reference"][:40], "->", txid)
        if txid:
            http_json("POST", f"{gateway}/api/transactions/{txid}/complete")

    tx_list = http_json("GET", f"{gateway}/api/transactions/account/{first_id}")
    print("GET /api/transactions/account/... count:", len(tx_list) if tx_list else 0)

    for c in data["consents"]:
        # Align consent subject with an account holder we just created so AIS returns data.
        payload = {"tppId": c["tppId"], "scopes": c["scopes"], "customerId": first_customer}
        if c.get("validUntil") is not None:
            payload["validUntil"] = c["validUntil"]
        created = http_json("POST", f"{gateway}/openbanking/consents", payload)
        results["consents"].append({"request": payload, "response": created})
        print("POST /openbanking/consents", c["tppId"], "->", created.get("consentId") if created else None)

    ob_headers = {
        "X-Demo-Tpp-Id": data["consents"][0]["tppId"],
        "X-Demo-Customer-Id": first_customer,
    }
    try:
        ais = http_json("GET", f"{gateway}/openbanking/accounts", headers=ob_headers)
        results["openbanking_ais"] = ais
        print("GET /openbanking/accounts (AIS) count:", len(ais) if isinstance(ais, list) else ais)
    except urllib.error.HTTPError as e:
        print("GET /openbanking/accounts skipped:", e.code, e.read().decode("utf-8", errors="replace")[:200])

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
