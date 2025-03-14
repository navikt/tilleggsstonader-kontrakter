package no.nav.tilleggsstonader.kontrakter.søknad

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.tilleggsstonader.kontrakter.FileUtil
import no.nav.tilleggsstonader.kontrakter.felles.ObjectMapperProvider.objectMapperFailOnUnknownProperties
import no.nav.tilleggsstonader.kontrakter.søknad.boutgifter.fyllutsendinn.MottarDuEllerHarDuNyligSoktOmNoeAvDette
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Path

class SøknadsskjemaBoutgifterFyllUtSendInnDataTest {

    val json = FileUtil.readFile("søknad/boutgifter/boutgifter.json")

    @ParameterizedTest
    @MethodSource("jsonfiler")
    fun `skal kunne parsea skjema`(filename: Path) {
        val json = FileUtil.readFile("søknad/boutgifter/$filename")
        assertDoesNotThrow {
            objectMapperFailOnUnknownProperties
                .readValue<SøknadsskjemaBoutgifterFyllUtSendInn>(json)
        }
    }

    @Nested
    inner class FeilVedManglendeFelter {
        @Test
        fun `skal feile hvis det finnes et ukjent felt med string`() {
            val jsonMap = objectMapperFailOnUnknownProperties.readValue<ObjectNode>(json)
            jsonMap.put("ukjentFelt", "test")
            val oppdatertJson = objectMapperFailOnUnknownProperties.writeValueAsString(jsonMap)

            assertThatThrownBy {
                objectMapperFailOnUnknownProperties
                    .readValue<SøknadsskjemaBoutgifterFyllUtSendInn>(oppdatertJson)
            }.hasMessageContaining("Unrecognized field \"ukjentFelt\" ")
        }

        @Test
        fun `skal feile hvis det finnes et ukjent felt med objekt`() {
            val jsonMap = objectMapperFailOnUnknownProperties.readValue<ObjectNode>(json)
            val data = jsonMap.get("data") as ObjectNode
            data.putPOJO("ukjentObjekt", mapOf("felt" to "felt"))
            val oppdatertJson = objectMapperFailOnUnknownProperties.writeValueAsString(jsonMap)

            assertThatThrownBy {
                objectMapperFailOnUnknownProperties
                    .readValue<SøknadsskjemaBoutgifterFyllUtSendInn>(oppdatertJson)
            }.hasMessageContaining("Unrecognized field \"ukjentObjekt\" ")
        }

        @Test
        fun `objekt uten @JsonIgnore skal feile`() {
            val jsonMap = objectMapperFailOnUnknownProperties.readValue<ObjectNode>(json)
            val data = jsonMap.get("data") as ObjectNode
            val innerData = data.get("data") as ObjectNode
            val mottarDuEllerHarDuNyligSoktOmNoeAvDette =
                innerData.get("mottarDuEllerHarDuNyligSoktOmNoeAvDette") as ObjectNode
            mottarDuEllerHarDuNyligSoktOmNoeAvDette.put("ukjentFelt", "test")
            val oppdatertJson = objectMapperFailOnUnknownProperties.writeValueAsString(jsonMap)

            assertThat(MottarDuEllerHarDuNyligSoktOmNoeAvDette::class.annotations).isEmpty()

            assertThatThrownBy {
                objectMapperFailOnUnknownProperties
                    .readValue<SøknadsskjemaBoutgifterFyllUtSendInn>(oppdatertJson)
            }.hasMessageContaining("Unrecognized field \"ukjentFelt\" (class no.nav.tilleggsstonader.kontrakter.søknad.boutgifter.fyllutsendinn.MottarDuEllerHarDuNyligSoktOmNoeAvDette")
        }
    }

    companion object {
        @JvmStatic
        fun jsonfiler() = FileUtil.listFiles("søknad/boutgifter").map { it.fileName }
    }
}
