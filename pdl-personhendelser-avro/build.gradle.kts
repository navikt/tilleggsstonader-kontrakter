val avroVersion = "1.12.0"
val confluentVersion = "8.0.1"

plugins {
    id("io.github.androa.gradle.plugin.avro") version "0.0.12"
}

generateAvro {
    schemas.setFrom("avro")
}

dependencies {
    implementation("org.apache.avro:avro:$avroVersion")
    implementation("io.confluent:kafka-avro-serializer:$confluentVersion")
}
