#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
export NVD_API_KEY="${NVD_API_KEY:-}"
for s in account-service transaction-service openbanking-service api-gateway fraud-detection-service core-simulator notification-service; do
  echo "=== dependencyCheckAnalyze: $s ==="
  (cd "$ROOT/backend/$s" && chmod +x gradlew && ./gradlew dependencyCheckAnalyze --no-daemon)
done
echo "Reports under each service build/reports/dependency-check-report.html"
