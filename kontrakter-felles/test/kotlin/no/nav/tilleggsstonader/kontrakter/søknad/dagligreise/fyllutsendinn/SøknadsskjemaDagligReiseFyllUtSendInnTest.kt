package no.nav.tilleggsstonader.kontrakter.søknad.dagligreise.fyllutsendinn

import no.nav.tilleggsstonader.kontrakter.FileUtil
import no.nav.tilleggsstonader.kontrakter.felles.JsonMapperProvider.jsonMapperFailOnUnknownProperties
import no.nav.tilleggsstonader.kontrakter.søknad.SøknadsskjemaDagligReiseFyllUtSendInn
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import tools.jackson.module.kotlin.readValue
import java.nio.file.Path

class SøknadsskjemaDagligReiseFyllUtSendInnTest {
    @ParameterizedTest
    @MethodSource("jsonfiler")
    fun `skal kunne parse skjema`(filename: Path) {
        val json = FileUtil.readFile("søknad/dagligreise/$filename")
        assertDoesNotThrow {
            jsonMapperFailOnUnknownProperties
                .readValue<SøknadsskjemaDagligReiseFyllUtSendInn>(json)
        }
    }

    /**
     * Fil er generert i [FyllUtSendInnSkjemaParser]
     */
    @Test
    fun `skal kunne parse eksempel fra skjema`() {
        val json = FileUtil.readFile("søknad/dagligreise/skjema-eksempel.json")
        assertDoesNotThrow {
            jsonMapperFailOnUnknownProperties
                .readValue<SøknadsskjemaDagligReiseFyllUtSendInn>(json)
        }
    }

    companion object {
        @JvmStatic
        fun jsonfiler() =
            FileUtil
                .listFiles("søknad/dagligreise")
                .filter { it.fileName.toString().startsWith("eksempel-") }
                .map { it.fileName }
    }
}
