package no.nav.tilleggsstonader.kontrakter.periode

import no.nav.tilleggsstonader.kontrakter.felles.Datoperiode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class AvkortKtTest {

    val datoperiode = Datoperiode(LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 3))

    @Nested
    inner class AvkortFraOgMed {
        @Test
        fun `nyTom er før periode - skal returnere null`() {
            assertThat(datoperiode.avkortFraOgMed(LocalDate.of(2025, 1, 1))).isNull()
        }

        @Test
        fun `nyTom er i periode - skal avkorte periode `() {
            assertThat(datoperiode.avkortFraOgMed(LocalDate.of(2025, 1, 2)))
                .isEqualTo(Datoperiode(LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 2)))
        }

        @Test
        fun `nyTom er lik eller etter periode - skal ikke avkorte periode`() {
            assertThat(datoperiode.avkortFraOgMed(LocalDate.of(2025, 1, 4))).isEqualTo(datoperiode)

            assertThat(datoperiode.avkortFraOgMed(LocalDate.of(2025, 1, 5))).isEqualTo(datoperiode)
        }
    }
}
