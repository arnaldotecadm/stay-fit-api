variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "eu-north-1"
}

variable "aws_account_id" {
  description = "AWS account ID"
  type        = string
  default     = "405633560433"
}

variable "app_name" {
  description = "Elastic Beanstalk application name"
  type        = string
  default     = "stay-fit-api"
}

variable "environment_name" {
  description = "Elastic Beanstalk environment name (e.g. stay-fit-staging)"
  type        = string
}

variable "instance_type" {
  description = "EC2 instance type"
  type        = string
  default     = "t3.micro"
}

variable "keypair_name" {
  description = "EC2 key pair name for SSH access"
  type        = string
  default     = "stay-fit-keypair"
}

# ── Deployment bundle ────────────────────────────────────────────────────────

variable "s3_bucket" {
  description = "S3 bucket that holds the deployment ZIP"
  type        = string
}

variable "s3_key" {
  description = "S3 key of the deployment ZIP (e.g. stay-fit-api/20240101-abc1234.zip)"
  type        = string
}

variable "version_label" {
  description = "Unique label for this application version"
  type        = string
}

# ── Database ─────────────────────────────────────────────────────────────────

variable "db_host" {
  description = "PostgreSQL host"
  type        = string
}

variable "db_port" {
  description = "PostgreSQL port"
  type        = string
  default     = "5432"
}

variable "db_name" {
  description = "PostgreSQL database name"
  type        = string
}

variable "db_username" {
  description = "PostgreSQL username"
  type        = string
  sensitive   = true
}

variable "db_password" {
  description = "PostgreSQL password"
  type        = string
  sensitive   = true
}

# ── App AWS credentials (used by the app at runtime, not by Terraform) ───────

variable "app_aws_access_key_id" {
  description = "AWS access key injected into the app environment"
  type        = string
  sensitive   = true
}

variable "app_aws_secret_access_key" {
  description = "AWS secret key injected into the app environment"
  type        = string
  sensitive   = true
}
