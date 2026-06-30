provider "aws" {
  region = var.aws_region
}

# ── Data sources ──────────────────────────────────────────────────────────────

data "aws_vpc" "default" {
  default = true
}

data "aws_subnets" "public" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.default.id]
  }
  filter {
    name   = "default-for-az"
    values = ["true"]
  }
}

data "aws_internet_gateway" "default" {
  filter {
    name   = "attachment.vpc-id"
    values = [data.aws_vpc.default.id]
  }
}

data "aws_route_table" "main" {
  vpc_id = data.aws_vpc.default.id
  filter {
    name   = "association.main"
    values = ["true"]
  }
}

data "aws_elastic_beanstalk_solution_stack" "java21" {
  most_recent = true
  # Java SE platform — Corretto 21 on Amazon Linux 2023, no Tomcat
  name_regex = "^64bit Amazon Linux 2023 .* running Corretto 21$"
}

# ── Networking ────────────────────────────────────────────────────────────────

# Ensure the default VPC's main route table has an internet-gateway route so
# instances in default subnets are publicly reachable.
resource "aws_default_route_table" "main" {
  default_route_table_id = data.aws_route_table.main.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = data.aws_internet_gateway.default.id
  }

  lifecycle {
    # Don't remove routes that EB or other services may add later.
    ignore_changes = [tags]
  }
}

# Dedicated security group — explicit rules, no surprises.
resource "aws_security_group" "eb_instance" {
  name        = "${var.app_name}-${var.environment_name}"
  description = "Stay Fit EB instance: HTTP and SSH"
  vpc_id      = data.aws_vpc.default.id

  ingress {
    description = "HTTP"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${var.app_name}-${var.environment_name}"
  }
}

# ── Elastic Beanstalk ─────────────────────────────────────────────────────────

resource "aws_elastic_beanstalk_application" "this" {
  name        = var.app_name
  description = "Stay Fit API"
}

resource "aws_elastic_beanstalk_application_version" "this" {
  name        = var.version_label
  application = aws_elastic_beanstalk_application.this.name
  bucket      = var.s3_bucket
  key         = var.s3_key

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_elastic_beanstalk_environment" "this" {
  name                = var.environment_name
  application         = aws_elastic_beanstalk_application.this.name
  solution_stack_name = data.aws_elastic_beanstalk_solution_stack.java21.name
  version_label       = aws_elastic_beanstalk_application_version.this.name

  # ── Environment ─────────────────────────────────────────────────────────────
  setting {
    namespace = "aws:elasticbeanstalk:environment"
    name      = "EnvironmentType"
    value     = "SingleInstance"
  }
  setting {
    namespace = "aws:elasticbeanstalk:environment"
    name      = "ServiceRole"
    value     = "arn:aws:iam::${var.aws_account_id}:role/ElasticBeanStalkServiceRole"
  }

  # ── EC2 / launch configuration ───────────────────────────────────────────────
  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "IamInstanceProfile"
    value     = "aws-elasticbeanstalk-ec2-role"
  }
  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "EC2KeyName"
    value     = var.keypair_name
  }
  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "InstanceType"
    value     = var.instance_type
  }
  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "RootVolumeType"
    value     = "gp2"
  }
  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "RootVolumeSize"
    value     = "10"
  }
  # Use our explicitly-defined security group instead of EB's auto-generated one.
  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "SecurityGroups"
    value     = aws_security_group.eb_instance.id
  }

  # ── VPC / networking ─────────────────────────────────────────────────────────
  setting {
    namespace = "aws:ec2:vpc"
    name      = "VPCId"
    value     = data.aws_vpc.default.id
  }
  setting {
    namespace = "aws:ec2:vpc"
    name      = "Subnets"
    value     = sort(data.aws_subnets.public.ids)[0]
  }
  setting {
    namespace = "aws:ec2:vpc"
    name      = "AssociatePublicIpAddress"
    value     = "true"
  }

  # ── Instances ────────────────────────────────────────────────────────────────
  setting {
    namespace = "aws:ec2:instances"
    name      = "EnableSpot"
    value     = "false"
  }

  # ── Health / monitoring ──────────────────────────────────────────────────────
  setting {
    namespace = "aws:elasticbeanstalk:healthreporting:system"
    name      = "SystemType"
    value     = "basic"
  }

  # ── Deployment ───────────────────────────────────────────────────────────────
  setting {
    namespace = "aws:elasticbeanstalk:command"
    name      = "Timeout"
    value     = "300"
  }

  # ── Application environment variables ────────────────────────────────────────
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "PORT"
    value     = "5000"
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "JAVA_OPTS"
    value     = "-Xms512m -Xmx1024m -XX:+UseG1GC"
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "AWS_REGION"
    value     = var.aws_region
  }

  # Database
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "DATABASE_HOST"
    value     = var.db_host
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "DATABASE_PORT"
    value     = var.db_port
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "DATABASE_NAME"
    value     = var.db_name
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "SPRING_DATASOURCE_USERNAME"
    value     = var.db_username
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "SPRING_DATASOURCE_PASSWORD"
    value     = var.db_password
  }

  # AWS credentials for the app (SQS access, etc.)
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "AWS_ACCESS_KEY_ID"
    value     = var.app_aws_access_key_id
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "AWS_SECRET_ACCESS_KEY"
    value     = var.app_aws_secret_access_key
  }

  # SQS queue URLs
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "AWS_SQS_HEART_RATE_SERIES_QUEUE_URL"
    value     = "https://sqs.${var.aws_region}.amazonaws.com/${var.aws_account_id}/HearRateSeriesQueue"
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "AWS_SQS_SLEEP_SESSION_QUEUE_URL"
    value     = "https://sqs.${var.aws_region}.amazonaws.com/${var.aws_account_id}/SleepSessionQueue"
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "AWS_SQS_EXERCISE_SESSION_QUEUE_URL"
    value     = "https://sqs.${var.aws_region}.amazonaws.com/${var.aws_account_id}/ExerciseSessionQueue"
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "AWS_SQS_DAILY_SUMMARY_QUEUE_URL"
    value     = "https://sqs.${var.aws_region}.amazonaws.com/${var.aws_account_id}/DailySummaryQueue"
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "SQS_MAX_CONCURRENT_MESSAGES"
    value     = "100"
  }
  setting {
    namespace = "aws:elasticbeanstalk:application:environment"
    name      = "SQS_MAX_MESSAGES_PER_POLL"
    value     = "10"
  }
}
