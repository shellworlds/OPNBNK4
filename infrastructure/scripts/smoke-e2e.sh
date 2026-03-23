#!/usr/bin/env bash
# Thin wrapper: runs Python smoke against a live docker-compose stack.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
export GATEWAY="${GATEWAY:-http://localhost:8080}"
exec python3 "$ROOT/infrastructure/scripts/smoke-e2e.py" --gateway "$GATEWAY"
