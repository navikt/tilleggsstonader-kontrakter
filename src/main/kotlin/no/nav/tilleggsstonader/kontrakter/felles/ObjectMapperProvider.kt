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
    val objectMapper: ObjectMapper = // Jackson2ObjectMapperBuilder.json().build()
        ObjectMapper()
            .registerKotlinModule()
            .registerModule(Jdk8Module())
            .registerModule(
                JavaTimeModule()
                    .addDeserializer(
                        YearMonth::class,
                        YearMonthDeserializer(DateTimeFormatter.ofPattern("u-MM")), // Denne trengs for å parse år over 9999 riktig.
                    ),
            ).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}
