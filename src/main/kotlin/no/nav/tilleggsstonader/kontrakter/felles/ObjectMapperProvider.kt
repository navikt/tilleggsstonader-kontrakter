package no.nav.tilleggsstonader.kontrakter.felles

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer
import com.fasterxml.jackson.module.kotlin.addDeserializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object ObjectMapperProvider {
    val javaTimeModule =
        JavaTimeModule()
            .addDeserializer(
                YearMonth::class,
                YearMonthDeserializer(DateTimeFormatter.ofPattern("u-MM")), // Denne trengs for å parse år over 9999 riktig.
            )

    private fun lagObjectMapper() =
        ObjectMapper()
            .registerKotlinModule()
            .registerModule(Jdk8Module())
            .registerModule(javaTimeModule)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)

    val objectMapper: ObjectMapper =
        lagObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    val objectMapperFailOnUnknownProperties =
        lagObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
}
