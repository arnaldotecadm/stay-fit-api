output "environment_url" {
  description = "Public URL of the Elastic Beanstalk environment"
  value       = "http://${aws_elastic_beanstalk_environment.this.cname}"
}

output "environment_id" {
  description = "Elastic Beanstalk environment ID"
  value       = aws_elastic_beanstalk_environment.this.id
}

output "security_group_id" {
  description = "ID of the instance security group (useful for adding extra rules)"
  value       = aws_security_group.eb_instance.id
}
