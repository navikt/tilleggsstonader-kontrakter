package no.nav.tilleggsstonader.kontrakter.kodeverk

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class KodeverkTest {
    val dato = LocalDate.now()

    @Test
    internal fun `KodeverkDto mapTerm mapper nykkel til term`() {
        val betydninger = mapOf(
            "NOR" to listOf(
                BetydningDto(
                    dato,
                    dato,
                    mapOf(
                        "nb" to BeskrivelseDto("NorgeTerm", "NorgeTekst"),
                    ),
                ),
            ),
            "SWE" to listOf(BetydningDto(dato, dato, mapOf())),
        )
        val kodeverkDto = KodeverkDto(betydninger)

        val expected = mapOf(
            "NOR" to "NorgeTerm",
            "SWE" to "",
        )
        assertThat(kodeverkDto.mapTerm()).isEqualTo(expected)
    }

    @Test
    internal fun `KodeverkDto mapTerm henter gjeldende når det finnes historikk`() {
        val betydninger = mapOf(
            "NOR" to listOf(
                BetydningDto(
                    LocalDate.of(2000, 1, 1),
                    LocalDate.of(2010, 1, 1),
                    mapOf("nb" to BeskrivelseDto("IkkeGjeldende", "IkkeGjeldende")),
                ),
                BetydningDto(
                    LocalDate.of(2010, 1, 2),
                    LocalDate.of(2099, 1, 2),
                    mapOf("nb" to BeskrivelseDto("Gjeldende", "Gjeldende")),
                ),
            ),
        )
        val kodeverkDto = KodeverkDto(betydninger)
        assertThat(kodeverkDto.mapTerm()["NOR"]).isEqualTo("Gjeldende")
    }
}
