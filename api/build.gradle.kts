plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jmailen.kotlinter")
//    id("org.flywaydb.flyway") version "9.19.4"
}

repositories {
    mavenCentral()
}

springBoot {
    buildInfo()
}

dependencies {
    implementation(project(":adapters"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.5")
    implementation("org.postgresql:postgresql:42.7.7")
//    implementation("org.flywaydb:flyway-core:9.19.4")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
/*
flyway {
    url = "jdbc:postgresql://${postgresHost}:${postgresPort}/${postgresDatabase}"
    user = postgresUsername
    password = postgresPassword
    locations = arrayOf("filesystem:./db/migration")
}
*/
tasks.test {
    useJUnitPlatform()
}