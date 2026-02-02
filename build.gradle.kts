val javaVersion = JavaLanguageVersion.of(21)

group = "no.nav.tilleggsstonader.kontrakter"

plugins {
    kotlin("jvm") version "2.3.0"
    `maven-publish`
    `java-library`
    id("com.diffplug.spotless") version "8.0.0"
    id("com.github.ben-manes.versions") version "0.53.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.19"
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
            ktlint("1.7.1")
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
        testImplementation(platform("org.junit:junit-bom:6.0.2"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
