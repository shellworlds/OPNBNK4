# Uncomment and supply a real resource group / subscription when Azure credits are available.
#
# resource "azurerm_resource_group" "main" {
#   name     = "rg-opnbnk4-${var.environment}"
#   location = var.location
# }
#
# resource "azurerm_kubernetes_cluster" "aks" {
#   name                = "aks-opnbnk4-${var.environment}"
#   location            = azurerm_resource_group.main.location
#   resource_group_name = azurerm_resource_group.main.name
#   dns_prefix          = "opnbnk4${var.environment}"
#
#   default_node_pool {
#     name                = "default"
#     node_count          = var.aks_node_count
#     vm_size             = var.aks_vm_size
#     enable_auto_scaling = true
#     min_count           = 2
#     max_count           = 8
#   }
#
#   identity {
#     type = "SystemAssigned"
#   }
# }
#
# # Azure Database for PostgreSQL Flexible Server, Redis, Key Vault, and Event Hubs / Confluent
# # should be provisioned in separate modules with private endpoints — see Azure Well-Architected.
