terraform {
  required_version = ">= 1.5"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  # Partial backend — bucket and key are supplied at `terraform init` time so
  # each environment (staging / prod) gets its own isolated state file.
  backend "s3" {
    region  = "eu-north-1"
    encrypt = true
  }
}
