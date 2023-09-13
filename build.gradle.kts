val javaVersion = JavaLanguageVersion.of(17)
val tilleggsstønaderLibsVersion = "2023.09.04-15.58.86e7da46c21d"
val tokenSupportVersion = "3.1.5"
val wiremockVersion = "2.35.0"
val mockkVersion = "1.13.7"
val testcontainerVersion = "1.19.0"

group = "no.nav.tilleggsstonader.kontrakter"
version = "1.0.0"

plugins {
    kotlin("jvm") version "1.9.10"
    `maven-publish`
    `java-library`
    id("com.diffplug.spotless") version "6.21.0"
    id("com.github.ben-manes.versions") version "0.48.0"
}

repositories {
    mavenCentral()
    mavenLocal()

    maven {
        url = uri("https://github-package-registry-mirror.gc.nav.no/cached/maven-release")
    }
}

apply(plugin = "com.diffplug.spotless")

spotless {
    kotlin {
        ktlint("0.50.0")
    }
}

apply(plugin = "org.jetbrains.kotlin.jvm")
apply(plugin = "maven-publish")
apply(plugin = "java-library")

configurations.all {
    resolutionStrategy {
        failOnNonReproducibleResolution()
    }
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.1.3"))

    implementation("com.fasterxml.jackson.core:jackson-annotations")

    testImplementation("org.junit.jupiter:junit-jupiter")
}

kotlin {
    jvmToolchain(javaVersion.asInt())

    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

if (project.hasProperty("skipLint")) {
    gradle.startParameter.excludedTaskNames += "spotlessKotlinCheck"
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.name
            version = project.findProperty("version")?.toString() ?: "1.0-SNAPSHOT"
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/navikt/tilleggsstonader-kontrakter")
            credentials {
                username = "x-access-token"
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
