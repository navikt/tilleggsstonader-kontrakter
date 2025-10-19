val avroVersion = "1.12.1"
val confluentVersion = "8.1.0"

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
