# Kubernetes manifests (AKS / staging)

**Namespace:** `opnbnk4`  
**Layout:** `base/` holds sample **Deployment**, **Service**, **ConfigMap**, **Ingress**, and a **Secret** placeholder for JDBC.

## Apply (dry run)

```bash
kubectl apply -k base/ --dry-run=client   # if you add kustomization.yaml
# or:
kubectl apply -f base/namespace.yaml
kubectl apply -f base/configmap-env.yaml
kubectl apply -f base/secret-db-placeholder.yaml   # replace with CSI-synced Secret in prod
kubectl apply -f base/account-service.yaml
kubectl apply -f base/api-gateway.yaml
kubectl apply -f base/ingress.yaml
```

## Images

Defaults point at **GHCR** tags `ghcr.io/shellworlds/opnbnk4-*:v0.9.0`. Push images from CI (`deploy-aks.yml`) or retag local builds.

## Helm

See `../helm/opnbnk4/` for a templated gateway chart; extend with subcharts per service in Day 5–7.

## Notes

- Add **transaction-service**, **openbanking-service**, **Kafka**, **Redis**, and **Postgres** via managed Azure services or in-cluster operators before production traffic.
- Wire **Azure Key Vault CSI** instead of committing real `Secret` manifests.
