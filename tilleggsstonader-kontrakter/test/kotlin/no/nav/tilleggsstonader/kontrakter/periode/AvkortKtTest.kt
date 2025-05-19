package no.nav.tilleggsstonader.kontrakter.periode

import no.nav.tilleggsstonader.kontrakter.felles.Datoperiode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class AvkortKtTest {
    val datoperiode = Datoperiode(LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 3))
    val datoperioder = listOf(datoperiode)
    val medTomFn: (Datoperiode, LocalDate) -> Datoperiode = { periode, nyttTom -> periode.copy(tom = nyttTom) }

    @Nested
    inner class AvkortPerioderFør {
        @Test
        fun `dato er før eller lik periode - skal returnere perioden`() {
            assertThat(datoperiode.avkortPerioderFør(LocalDate.of(2025, 1, 1))).isEqualTo(datoperiode)
            assertThat(datoperiode.avkortPerioderFør(LocalDate.of(2025, 1, 2))).isEqualTo(datoperiode)
        }

        @Test
        fun `dato er etter periode - skal returnere null`() {
            assertThat(datoperiode.avkortPerioderFør(LocalDate.of(2025, 1, 4))).isNull()
        }

        @Test
        fun `dato er en del av tidligere periode, skal avkorte frem til dato`() {
            assertThat(datoperiode.avkortPerioderFør(LocalDate.of(2025, 1, 3)))
                .isEqualTo(Datoperiode(LocalDate.of(2025, 1, 3), LocalDate.of(2025, 1, 3)))
        }

        @Test
        fun `hele perioden skal beholdes hvis dato er null`() {
            assertThat(datoperiode.avkortPerioderFør(null)).isEqualTo(datoperiode)
        }
    }

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

    @Nested
    inner class AvkortFraOgMedMedResultat {
        @Test
        fun `nyTom er før periode - skal returnere tom liste`() {
            val avkortet = datoperioder.avkortFraOgMed(LocalDate.of(2025, 1, 1), medTomFn)

            assertThat(avkortet.perioder).isEmpty()
            assertThat(avkortet.harAvkortetPeriode).isFalse()
        }

        @Test
        fun `nyTom er i periode - skal avkorte periode`() {
            val avkortet = datoperioder.avkortFraOgMed(LocalDate.of(2025, 1, 2), medTomFn)

            assertThat(avkortet.perioder.single())
                .isEqualTo(Datoperiode(LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 2)))
            assertThat(avkortet.harAvkortetPeriode).isTrue()
        }

        @Test
        fun `nyTom er lik forrige tom - har ikke splittet periode`() {
            val avkortet = datoperioder.avkortFraOgMed(LocalDate.of(2025, 1, 3), medTomFn)

            assertThat(avkortet.perioder).isEqualTo(datoperioder)
            assertThat(avkortet.harAvkortetPeriode).isFalse()
        }

        @Test
        fun `nyTom er lik eller etter periode - skal ikke avkorte periode`() {
            val avkortet = datoperioder.avkortFraOgMed(LocalDate.of(2025, 1, 4), medTomFn)

            assertThat(avkortet.perioder).isEqualTo(datoperioder)
            assertThat(avkortet.harAvkortetPeriode).isFalse()
        }
    }
}
