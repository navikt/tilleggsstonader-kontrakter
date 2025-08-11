package no.nav.tilleggsstonader.kontrakter.søknad.dagligreise.fyllutsendinn

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.tilleggsstonader.kontrakter.FileUtil
import no.nav.tilleggsstonader.kontrakter.felles.ObjectMapperProvider.objectMapperFailOnUnknownProperties
import no.nav.tilleggsstonader.kontrakter.søknad.SøknadsskjemaDagligreiseFyllUtSendInn
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Path

class SøknadsskjemaDagligReiseFyllUtSendInnTest {
    @ParameterizedTest
    @MethodSource("jsonfiler")
    fun `skal kunne parsea skjema`(filename: Path) {
        val json = FileUtil.readFile("søknad/dagligreise/$filename")
        assertDoesNotThrow {
            objectMapperFailOnUnknownProperties
                .readValue<SøknadsskjemaDagligreiseFyllUtSendInn>(json)
        }
    }

    /**
     * Fil er generert i [FyllUtSendInnSkjemaParser]
     */
    @Test
    fun `skal kunne parsea eksempel fra skjema`() {
        val json = FileUtil.readFile("søknad/dagligreise/skjema-eksempel.json")
        assertDoesNotThrow {
            objectMapperFailOnUnknownProperties
                .readValue<SkjemaDagligreise>(json)
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
