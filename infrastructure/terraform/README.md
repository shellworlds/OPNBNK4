# Terraform — Azure landing zone (skeleton)

`main.tf` pins provider versions. `aks.tf` contains **commented** resources for **AKS**, to be enabled when subscription and remote state (e.g. Azure Storage) are configured.

## Intended resources

- AKS with auto-scaling node pool  
- Azure Database for PostgreSQL Flexible Server  
- Azure Cache for Redis  
- Azure Key Vault  
- Managed Kafka (Azure Event Hubs for Kafka or Confluent) — pick per enterprise standards  
- Application Gateway or Azure Front Door for ingress TLS

## Next steps

1. `az login` and select subscription.  
2. Create backend state container.  
3. Uncomment `provider "azurerm"` in `main.tf` and resources in `aks.tf`.  
4. `terraform init && terraform plan`.
