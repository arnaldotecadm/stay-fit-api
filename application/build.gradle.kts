plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spring)
    alias(libs.plugins.klinter)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.springStarterWeb)
    implementation(libs.springStarterSecurity)
    implementation(libs.springStarterDataJpa)
    implementation(libs.servletApi)
    implementation(libs.nimbusJoseJWT)
}

tasks.test {
    useJUnitPlatform()
}