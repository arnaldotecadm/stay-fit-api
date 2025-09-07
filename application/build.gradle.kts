plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.jmailen.kotlinter")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.5")
}

tasks.test {
    useJUnitPlatform()
}