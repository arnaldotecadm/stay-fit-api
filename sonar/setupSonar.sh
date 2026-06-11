#!/usr/bin/env bash
<<comment
 This script is worth it for when you run multiple times the sonar qube locally and you wanna see the results on your machine
 First of all we check whether or not we have already changed the password
 We use the password for a health check against the sonarQube
 Once sonarQube is up and running, we change the password to a new one

 nb1: if we do not do that, the user have to change it first time they access sonarQube
comment

set -e #Exit script on failure

LOCAL_SONAR_HOST=http://localhost:10000

function main() {
  echo "Starting Sonarqube"
  healthCheck $LOCAL_SONAR_HOST
  echo "Sonarqube up and running"
}

function healthCheck() {
  echo "Waiting for Sonar to start on $LOCAL_SONAR_HOST"

  PASSWORD_HEALTH_CHECK=$(getCurrentSonarPassword "$1")

  while [[ "$(curl -s -u "admin:$PASSWORD_HEALTH_CHECK" "$1/api/system/health" | jq -r ''.health'')" != "GREEN" ]]; do sleep 2; done

    if [[ "$PASSWORD_HEALTH_CHECK" -eq "admin" ]]; then
      echo "Changing the default password for sonarQube"
      changeDefaultPassword
    fi
}

function changeDefaultPassword() {
  curl -u admin:admin -X POST "http://localhost:10000/api/users/change_password?login=admin&previousPassword=admin&password=@MYexpenses"
  echo "You can now access your local SonarQube instance with the credentials admin:@MYexpenses"
  echo "Have fun!"
}

function getCurrentSonarPassword() {
  #checks whether or not the password has already been changed
  #in case it has already been changed, then use the new one for the health check

  PASSWORD_HEALTH_CHECK="admin"
  isPasswordAlreadyChanged=$(curl --write-out '%{http_code}' --silent --output /dev/null -u "admin:admin" "$1/api/system/health")

  if [[ "$isPasswordAlreadyChanged" -eq 401 ]]; then
    PASSWORD_HEALTH_CHECK="@MYexpenses"
  fi

  echo $PASSWORD_HEALTH_CHECK
}

main
