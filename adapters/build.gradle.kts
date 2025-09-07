plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.15.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":application"))
    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-web:3.5.5")

    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.20")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.36")
    compileOnly("javax.servlet:servlet-api:3.0-alpha-1")
    implementation("io.swagger.core.v3:swagger-models:2.2.36")
    implementation("jakarta.validation:jakarta.validation-api:3.1.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.19.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.5")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

val openApiGeneratedResources = "openapi-generated"
val openApiSrcFolder = "$buildDir/$openApiGeneratedResources"
val openApiKotlinSrcFolder = "$openApiSrcFolder/src/main/kotlin"
openApiGenerate {
    inputSpec.set("$rootDir/adapters/src/main/resources/static/openapi/openapi.yaml")
    generatorName.set("kotlin-spring")
    library.set("spring-boot")
    outputDir.set(openApiSrcFolder)
    configOptions.set(
        mapOf(
            "useSpringBoot3" to "true",
            "useJakartaEe" to "true",
            "interfaceOnly" to "true",
            "useBeanValidation" to "true",
            "performBeanValidation" to "true",
            "modelPackage" to "br.com.arcasoftware.stayfit.model",
            "apiPackage" to "br.com.arcasoftware.stayfit.controller",
            "serializableModel" to "true",
            "developerEmail" to "arnaldotecadm@hotmail.com",
            "developerOrganizationUrl" to "https://www.linkedin.com/in/arnaldocicero",
            "skipDefaultInterface" to "true"
        )
    )
    typeMappings.set(
        mapOf(
            "instant" to "Instant",
            "zone-offset" to "ZoneOffset",
            "duration" to "Duration"
        )
    )
    importMappings.set(
        mapOf(
            "Instant" to "java.time.Instant",
            "ZoneOffset" to "java.time.ZoneOffset",
            "Duration" to "java.time.Duration",
        )
    )
}

sourceSets {
    main {
        java {
            setSrcDirs(srcDirs.plus(openApiKotlinSrcFolder))
        }
    }
}

tasks {
    build {
        dependsOn("openApiGenerate")
    }
    compileKotlin {
        dependsOn("openApiGenerate")
    }
}