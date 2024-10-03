val javaVersion = JavaLanguageVersion.of(21)
val tilleggsstønaderLibsVersion = "2024.07.16-13.33.660176a23947"

group = "no.nav.tilleggsstonader.kontrakter"

plugins {
    kotlin("jvm") version "2.0.20"
    `maven-publish`
    `java-library`
    id("com.diffplug.spotless") version "6.25.0"
    id("com.github.ben-manes.versions") version "0.51.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.18"
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
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.3.3"))

    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")

    // Tillegggsstønader libs
    implementation("no.nav.tilleggsstonader-libs:util:$tilleggsstønaderLibsVersion")
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
