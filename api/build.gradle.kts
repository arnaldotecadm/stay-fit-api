plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spring)
    alias(libs.plugins.klinter)
    alias(libs.plugins.springbootPlugin)
    alias(libs.plugins.springbootManagement)
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
    implementation(libs.springStarterWeb)
    implementation(libs.postgres)
//    implementation("org.flywaydb:flyway-core:9.19.4")
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