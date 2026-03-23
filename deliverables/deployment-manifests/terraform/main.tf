# Placeholder: Azure resource definitions (Day 7+).
# Wire providers, state backend, and modules for AKS, Key Vault, etc.

terraform {
  required_version = ">= 1.5.0"
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.100"
    }
  }
}

# Uncomment when ready to target Azure:
# provider "azurerm" {
#   features {}
# }
