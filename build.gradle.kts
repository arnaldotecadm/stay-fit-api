plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.klinter) apply false
    id("org.sonarqube") version "4.0.0.2929"
    id("com.avast.gradle.docker-compose") version ("0.16.11")
}

group = "br.com.arcasoftware"
version = "0.0.1-SNAPSHOT"
description = "Application for extracting data from Samsung Health and push them into a webservice"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {

    val changePassword by registering {
        doFirst {
            exec {
                commandLine("./sonar/setupSonar.sh")
            }
        }
    }

    val runSonar by registering {
        //change the default password for sonar, so that we can access it later without changing the password
        dependsOn(changePassword)
        //run sonarQube
        dependsOn(sonar)
        //shutdown docker-compose container
        //finalizedBy(composeDown)
    }

    val startAnalysis by registering {
        //start docker-compose container
        dependsOn(composeUp)

        //run Sonar qube
        dependsOn(runSonar)
    }

}

tasks.register("stage") {
    dependsOn("clean", ":api:build")
}

dockerCompose {
    useComposeFiles.add("sonar/docker-compose.yml")
}

sonar {
    properties {
        property("sonar.host.url", "http://localhost:11000")
        property("sonar.projectKey", "stay-fit-api")
        property("sonar.projectName", "Stay Fit API")
        property("sonar.token", "sqa_433e3806b5f757e01555f3a3e8214a950aa49095")
    }
}