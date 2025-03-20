package no.nav.tilleggsstonader.kontrakter.søknad.boutgifter.fyllutsendinn

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.tilleggsstonader.kontrakter.FileUtil
import no.nav.tilleggsstonader.kontrakter.felles.ObjectMapperProvider.objectMapperFailOnUnknownProperties
import no.nav.tilleggsstonader.kontrakter.søknad.SøknadsskjemaBoutgifterFyllUtSendInn
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Path

class SøknadsskjemaBoutgifterFyllUtSendInnTest {
    val json = FileUtil.readFile("søknad/boutgifter/eksempel-boutgifter.json")

    /**
     * Flyt som burde testes:
     * * Arbeid utenfor norge: eksempel-arbeid-utenfor-norge.json - Gjennom å velge eks målgruppe tiltakspenger
     * * Person uten aktiviteter: eksempel-uten-aktiviter.json
     *
     * Hva slags utgifter søker du om å få støtte til?
     * * Faste utgifter: eksempel-faste-utgifter.json
     * * Utgifter til overnatting: eksempel-boutgifter.json
     *
     * Send in skjema [https://skjemadelingslenke.ekstern.dev.nav.no/fyllut/nav111219]
     * Finn [journalpostId] og [dokumentId] fra dokumentoversikt til bruker i TS eller gosys
     *
     * Finn json til skjema fra
     * https://saf-q2.dev.intern.nav.no/rest/hentdokument/<journalpostId>/<dokumentId>/ORIGINAL
     * Må ha token integrasjoner -> saf
     */
    @ParameterizedTest
    @MethodSource("jsonfiler")
    fun `skal kunne parsea skjema`(filename: Path) {
        val json = FileUtil.readFile("søknad/boutgifter/$filename")
        assertDoesNotThrow {
            objectMapperFailOnUnknownProperties
                .readValue<SøknadsskjemaBoutgifterFyllUtSendInn>(json)
        }
    }

    /**
     * Fil er generert i [FyllUtSendInnSkjemaParser]
     */
    @Test
    fun `skal kunne parsea eksempel fra skjema`() {
        val json = FileUtil.readFile("søknad/boutgifter/skjema-eksempel.json")
        assertDoesNotThrow {
            objectMapperFailOnUnknownProperties
                .readValue<SkjemaBoutgifter>(json)
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
            val hovedytelse = innerData.get("hovedytelse") as ObjectNode
            hovedytelse.put("ukjentFelt", "test")
            val oppdatertJson = objectMapperFailOnUnknownProperties.writeValueAsString(jsonMap)

            assertThat(Hovedytelse::class.annotations).isEmpty()

            assertThatThrownBy {
                objectMapperFailOnUnknownProperties
                    .readValue<SøknadsskjemaBoutgifterFyllUtSendInn>(oppdatertJson)
            }.hasMessageContaining(
                "Unrecognized field \"ukjentFelt\"",
            )
        }
    }

    companion object {
        @JvmStatic
        fun jsonfiler() =
            FileUtil
                .listFiles("søknad/boutgifter")
                .filter { it.fileName.toString().startsWith("eksempel-") }
                .map { it.fileName }
    }
}
