package no.nav.tilleggsstonader.kontrakter.felles

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PeriodeSplitPerÅrTest {

    private val FØRSTE_JAN_2024 = LocalDate.of(2024, 1, 1)
    private val SISTE_DES_2024 = LocalDate.of(2024, 12, 31)
    private val FØRSTE_JAN_2025 = LocalDate.of(2025, 1, 1)
    private val SISTE_DES_2025 = LocalDate.of(2025, 12, 31)
    private val FØRSTE_JAN_2026 = LocalDate.of(2026, 1, 1)


    @Test
    fun `skal ikke splitte periode som er innenfor et og samme år`() {
        val datoperiode = Datoperiode(FØRSTE_JAN_2024, SISTE_DES_2024)
        assertThat(datoperiode.splitPerÅr { fom, tom -> datoperiode.copy(fom = fom, tom = tom) })
            .containsExactly(datoperiode)
    }

    @Test
    fun `skal splitte periode som løper til første jan neste år i 2 perioder`() {
        val datoperiode = Datoperiode(FØRSTE_JAN_2024, FØRSTE_JAN_2025)
        assertThat(datoperiode.splitPerÅr { fom, tom -> datoperiode.copy(fom = fom, tom = tom) })
            .containsExactly(
                Datoperiode(FØRSTE_JAN_2024, SISTE_DES_2024),
                Datoperiode(FØRSTE_JAN_2025, FØRSTE_JAN_2025)
            )
    }

    @Test
    fun `skal splitte periode som løper til neste år i 2 perioder`() {
        val tom = FØRSTE_JAN_2025.plusDays(1)
        val datoperiode = Datoperiode(FØRSTE_JAN_2024, tom)
        assertThat(datoperiode.splitPerÅr { fom, tom -> datoperiode.copy(fom = fom, tom = tom) })
            .containsExactly(
                Datoperiode(FØRSTE_JAN_2024, SISTE_DES_2024),
                Datoperiode(FØRSTE_JAN_2025, tom)
            )
    }

    @Test
    fun `skal splitte periode som løper til nestneste år i 3 perioder`() {
        val tom = LocalDate.of(2026, 9, 1)
        val datoperiode = Datoperiode(FØRSTE_JAN_2024, tom)
        assertThat(datoperiode.splitPerÅr { fom, tom -> datoperiode.copy(fom = fom, tom = tom) })
            .containsExactly(
                Datoperiode(FØRSTE_JAN_2024, SISTE_DES_2024),
                Datoperiode(FØRSTE_JAN_2025, SISTE_DES_2025),
                Datoperiode(FØRSTE_JAN_2026, tom)
            )
    }
}