val javaVersion = JavaLanguageVersion.of(21)

group = "no.nav.tilleggsstonader.kontrakter"

plugins {
    kotlin("jvm") version "2.1.20"
    `maven-publish`
    `java-library`
    id("com.diffplug.spotless") version "7.0.2"
    id("com.github.ben-manes.versions") version "0.52.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.18"
    id("org.cyclonedx.bom") version "2.2.0"
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
        ktlint("1.5.0")
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
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.4.4"))

    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")
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

tasks.cyclonedxBom {
    setIncludeConfigs(listOf("runtimeClasspath", "compileClasspath"))
}
