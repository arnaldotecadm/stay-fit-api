plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spring)
    alias(libs.plugins.klinter)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.springStarterSecurity)
}