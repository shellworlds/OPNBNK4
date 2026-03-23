variable "location" {
  type        = string
  description = "Azure region"
  default     = "westeurope"
}

variable "environment" {
  type        = string
  description = "Name suffix for resources"
  default     = "staging"
}

variable "aks_node_count" {
  type        = number
  default     = 3
}

variable "aks_vm_size" {
  type        = string
  default     = "Standard_D4s_v5"
}
