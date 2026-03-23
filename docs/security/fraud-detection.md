# Fraud detection (Day 3)

## Goals

- Provide a **deterministic rules layer** before transaction completion.
- Emit **async review signals** from Kafka for operations and future ML.

## Components

| Piece | Role |
|--------|------|
| **fraud-detection-service** | Rule engine (`RuleEngine`), REST `POST /api/fraud/evaluate`, Kafka consumer on `bank.transaction.created` |
| **transaction-service** | Calls evaluate when `fraud.evaluation.enabled=true`; blocks on `BLOCK` verdict |
| **ML placeholder** | `POST /api/fraud/ml/predict` returns a dummy score/label; replace with a trained model served via Python/ONNX |

## Rules (EUR equivalent)

- Amount over **5,000** EUR equivalent → `REVIEW`
- Amount over **50,000** EUR equivalent → `BLOCK`
- Missing device fingerprint → `REVIEW`
- First transaction from a **new device** for an account → `REVIEW`

FX rates in `RuleEngine` are illustrative stubs, not market rates.

## Headers

- `X-Device-Id` on `POST /api/transactions/{id}/complete` is passed to the fraud service as `deviceFingerprint`.

## Production direction

- Train a classifier (for example scikit-learn) on labelled transactions; expose inference behind the ML route or a sidecar.
- Persist device history and velocity features in a fraud datastore.
- Route `REVIEW` outcomes to a case-management queue instead of only logging.
