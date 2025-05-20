package no.nav.tilleggsstonader.kontrakter.felles

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.tilleggsstonader.kontrakter.felles.ObjectMapperProvider.objectMapper
import no.nav.tilleggsstonader.kontrakter.felles.ObjectMapperProvider.objectMapperFailOnUnknownProperties
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.LocalDate
import java.time.LocalDateTime

class ObjectMapperProviderTest {
    val expectedJson =
        """
        {
          "string" : "verdi",
          "manglerVerdi" : null,
          "dato" : "2023-01-01",
          "tidspunkt" : "2023-01-01T04:04:00",
          "liste" : [ {
            "string" : "verdi"
          } ],
          "set" : [ {
            "string" : "verdi"
          } ]
        }
        """.trimIndent()

    private val prettyPrinter = objectMapper.writerWithDefaultPrettyPrinter()

    @Test
    fun `skal parsea json riktig`() {
        val root = Root()
        val json = prettyPrinter.writeValueAsString(root)
        assertThat(json).isEqualTo(expectedJson)

        val rootFraJson = objectMapper.readValue<Root>(json)
        assertThat(prettyPrinter.writeValueAsString(rootFraJson)).isEqualTo(expectedJson)
    }

    @Test
    fun `skal kunne parsea data når en optional string mangler`() {
        assertThat(objectMapper.readValue<ElementMedOptionalFelt>("""{"verdi": "verdi"}"""))
            .isEqualTo(ElementMedOptionalFelt("verdi"))
    }

    @Test
    fun `skal feile hvis en påkrevd string mangler`() {
        assertThatThrownBy {
            objectMapper.readValue<ElementMedOptionalFelt>("""{}""")
        }.isInstanceOf(MismatchedInputException::class.java)
    }

    @Test
    fun `skal kunne parsea data når en boolean string mangler`() {
        assertThat(objectMapper.readValue<ElementMedOptionalBoolean>("""{"verdi": true}"""))
            .isEqualTo(ElementMedOptionalBoolean(true))
    }

    @Test
    fun `skal feile hvis en påkrevd boolean mangler`() {
        assertThatThrownBy {
            objectMapper.readValue<ElementMedOptionalBoolean>("""{}""")
        }.isInstanceOf(MismatchedInputException::class.java)
    }

    @Test
    fun `skal feile hvis en påkrevd boolean er null`() {
        assertThatThrownBy {
            objectMapper.readValue<ElementMedOptionalBoolean>("""{"verdi": null}""}""")
        }.isInstanceOf(MismatchedInputException::class.java)
    }

    @Test
    fun `skal sette defaultverdi når det finnes`() {
        assertThat(objectMapper.readValue<Element>("""{}"""))
            .isEqualTo(Element("verdi"))
    }

    @Nested
    inner class UnknownProperties {
        val json = """{"verdi": "verdi", "ukjentVerdi": "o"}"""

        @Nested
        inner class VanligObjectMapper {
            @Test
            fun `ignoreUnknown=true - skal ikke feile hvis ukjent felt finnes`() {
                assertDoesNotThrow {
                    objectMapper.readValue<IgnoreUnknownTestObjects.IgnoreUnknownTrue>(json)
                }
            }

            @Test
            fun `ignoreUnknown=false - skal ikke feile ved ukjente felter`() {
                assertDoesNotThrow {
                    objectMapper.readValue<IgnoreUnknownTestObjects.IgnoreUnknownFalse>(json)
                }
            }

            @Test
            fun `uten annotasjon - skal ikke feile ved ukjente felter`() {
                assertDoesNotThrow {
                    objectMapper.readValue<IgnoreUnknownTestObjects.UtenAnnotasjon>(json)
                }
            }
        }

        @Nested
        inner class ObjectMapperFailOnUnknownProperties {
            @Test
            fun `ignoreUnknown=true - skal ikke feile hvis ukjent felt finnes`() {
                assertDoesNotThrow {
                    objectMapperFailOnUnknownProperties.readValue<IgnoreUnknownTestObjects.IgnoreUnknownTrue>(json)
                }
            }

            @Test
            fun `ignoreUnknown=false - skal feile ved ukjente felter`() {
                assertThatThrownBy {
                    objectMapperFailOnUnknownProperties.readValue<IgnoreUnknownTestObjects.IgnoreUnknownFalse>(json)
                }.hasMessageContaining("Unrecognized field \"ukjentVerdi\"")
            }

            @Test
            fun `ignoreUnknown=false - skal feile ved ukjente felter i nestede object som ikke har annotasjon`() {
                val json = """{"verdi": "verdi", "utenAnnotation": {"verdi": "verdi", "ukjentVerdi": "o"}}"""
                assertThatThrownBy {
                    objectMapperFailOnUnknownProperties.readValue<IgnoreUnknownTestObjects.IgnoreUnknownFalseNested>(json)
                }.hasMessageContaining("Unrecognized field \"ukjentVerdi\"")
            }

            @Test
            fun `uten annotasjon - skal feile ved ukjente felter`() {
                assertThatThrownBy {
                    objectMapperFailOnUnknownProperties.readValue<IgnoreUnknownTestObjects.UtenAnnotasjon>(json)
                }.hasMessageContaining("Unrecognized field \"ukjentVerdi\"")
            }
        }
    }
}

private data class Root(
    val string: String = "verdi",
    val manglerVerdi: String? = null,
    val dato: LocalDate = LocalDate.of(2023, 1, 1),
    val tidspunkt: LocalDateTime = LocalDateTime.of(2023, 1, 1, 4, 4, 0),
    val liste: List<Element> = listOf(Element()),
    val set: Set<Element> = setOf(Element()),
)

private data class Element(
    val string: String = "verdi",
)

private data class ElementMedOptionalFelt(
    val verdi: String,
    val optional: String? = null,
)

private data class ElementMedOptionalBoolean(
    val verdi: Boolean,
    val optional: Boolean? = null,
)

private object IgnoreUnknownTestObjects {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class IgnoreUnknownTrue(
        val verdi: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = false)
    data class IgnoreUnknownFalse(
        val verdi: String,
    )

    data class UtenAnnotasjon(
        val verdi: String,
    )

    @JsonIgnoreProperties(ignoreUnknown = false)
    data class IgnoreUnknownFalseNested(
        val verdi: String,
        val utenAnnotation: UtenAnnotasjon,
    )
}
