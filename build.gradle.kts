val javaVersion = JavaLanguageVersion.of(21)

group = "no.nav.tilleggsstonader.kontrakter"

plugins {
    kotlin("jvm") version "2.1.21"
    `maven-publish`
    `java-library`
    id("com.diffplug.spotless") version "7.0.4"
    id("com.github.ben-manes.versions") version "0.52.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.18"
    id("org.cyclonedx.bom") version "2.3.1"
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://packages.confluent.io/maven/")
        maven("https://github-package-registry-mirror.gc.nav.no/cached/maven-release")
    }

    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "com.github.ben-manes.versions")
    apply(plugin = "se.patrikerdes.use-latest-versions")

    spotless {
        kotlin {
            ktlint("1.5.0")
        }
    }

    configurations.all {
        resolutionStrategy {
            failOnNonReproducibleResolution()
        }
    }
}

subprojects {
    group = "no.nav.tilleggsstonader.kontrakter"

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")

    kotlin {
        jvmToolchain(javaVersion.asInt())

        compilerOptions {
            freeCompilerArgs.add("-Xjsr305=strict")
        }
    }

    dependencies {
        testImplementation("io.mockk:mockk:1.14.2")
    }

    tasks {
        jar {
            duplicatesStrategy = DuplicatesStrategy.WARN
        }

        test {
            useJUnitPlatform()
        }
    }

    java {
        withSourcesJar()
        withJavadocJar()
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

    if (project.hasProperty("skipLint")) {
        gradle.startParameter.excludedTaskNames += "spotlessKotlinCheck"
    }

    kotlin.sourceSets["main"].kotlin.srcDirs("main/kotlin")
    kotlin.sourceSets["test"].kotlin.srcDirs("test/kotlin")
    sourceSets["main"].resources.srcDirs("main/resources")
    sourceSets["test"].resources.srcDirs("test/resources")
}

tasks.cyclonedxBom {
    setIncludeConfigs(listOf("runtimeClasspath", "compileClasspath"))
}
