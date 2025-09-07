import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register
import org.jmailen.gradle.kotlinter.tasks.FormatTask
import org.jmailen.gradle.kotlinter.tasks.LintTask

plugins {
	kotlin("jvm") version "1.9.25"
    id("org.jmailen.kotlinter") version "3.12.0" apply false
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
