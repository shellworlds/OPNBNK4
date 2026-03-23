# Production deployment guide — Azure AKS

## Prerequisites

- Azure subscription, resource group, and permissions for AKS, ACR or GHCR pull, Key Vault.
- `kubectl`, `helm`, `az` CLI configured.
- GitHub **environments** with secrets: `AZURE_CREDENTIALS`, `AKS_RG`, `AKS_NAME` (for optional Helm job).

## Image pipeline

1. Tag release: `git tag -a v1.0.0 -m "Production release"` and `git push origin v1.0.0`.
2. Workflow **Deploy AKS (tag)** builds and pushes images to `ghcr.io/shellworlds/opnbnk4-*:v1.0.0`.
3. In AKS, create `imagePullSecret` if pulling from GHCR with a PAT.

## Kubernetes

```bash
kubectl apply -f infrastructure/k8s/base/namespace.yaml
kubectl apply -f infrastructure/k8s/base/configmap-env.yaml
# Replace placeholder DB secret with Key Vault CSI–backed Secret
kubectl apply -f infrastructure/k8s/base/secret-db-placeholder.yaml   # dev only
kubectl apply -f infrastructure/k8s/base/account-service.yaml
kubectl apply -f infrastructure/k8s/base/api-gateway.yaml
kubectl apply -f infrastructure/k8s/base/ingress.yaml
```

Or Helm:

```bash
helm upgrade --install opnbnk4 ./infrastructure/helm/opnbnk4 -n opnbnk4 --create-namespace
```

## Post-deploy smoke

- `kubectl get pods -n opnbnk4` (or `digital-banking` if you rename namespace).
- `curl -sf https://<host>/actuator/health` on gateway.
- Run Playwright against production base URL (separate CI job).

## Rollback

```bash
helm rollback opnbnk4 -n opnbnk4
# or
kubectl rollout undo deployment/api-gateway -n opnbnk4
```

Document each production deploy in `GO_LIVE.md` (append-only log optional).
