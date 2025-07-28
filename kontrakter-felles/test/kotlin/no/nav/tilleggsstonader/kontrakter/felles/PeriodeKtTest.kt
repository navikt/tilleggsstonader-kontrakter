package no.nav.tilleggsstonader.kontrakter.felles

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PeriodeKtTest {
    @Nested
    inner class PåfølgesAv {
        @Test
        fun `dato påfølges ikke hvis de er overlappende`() {
            val datoperiode = Datoperiode(LocalDate.now(), LocalDate.now())
            val datoperiode2 = Datoperiode(LocalDate.now(), LocalDate.now().plusDays(1))
            assertThat(datoperiode.påfølgesAv(datoperiode)).isFalse
            assertThat(datoperiode.påfølgesAv(datoperiode2)).isFalse
        }

        @Test
        fun `dato påfølges ikke når de ikke er sammenhengende`() {
            val datoperiode = Datoperiode(LocalDate.now(), LocalDate.now())
            val datoperiode2 = Datoperiode(LocalDate.now().plusDays(2), LocalDate.now().plusDays(2))
            assertThat(datoperiode.påfølgesAv(datoperiode2)).isFalse
        }

        @Test
        fun `dato påfølges hvis tom plus en dag er lik fom`() {
            val datoperiode = Datoperiode(LocalDate.now(), LocalDate.now())
            val datoperiode2 = Datoperiode(LocalDate.now().plusDays(1), LocalDate.now().plusDays(1))
            val datoperiode3 = Datoperiode(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2))
            assertThat(datoperiode.påfølgesAv(datoperiode2)).isTrue
            assertThat(datoperiode.påfølgesAv(datoperiode3)).isTrue
        }
    }

    @Nested
    inner class OverlapperEllerPåfølgesAv {
        @Test
        fun `datoer overlapper`() {
            val datoperiode = Datoperiode(LocalDate.now(), LocalDate.now())
            val datoperiode2 = Datoperiode(LocalDate.now(), LocalDate.now().plusDays(1))
            assertThat(datoperiode.overlapperEllerPåfølgesAv(datoperiode)).isTrue
            assertThat(datoperiode.overlapperEllerPåfølgesAv(datoperiode2)).isTrue
        }

        @Test
        fun `datoer hverken overlapper eller påfølges av`() {
            val datoperiode = Datoperiode(LocalDate.now(), LocalDate.now())
            val datoperiode2 = Datoperiode(LocalDate.now().plusDays(2), LocalDate.now().plusDays(2))
            assertThat(datoperiode.overlapperEllerPåfølgesAv(datoperiode2)).isFalse
        }

        @Test
        fun `dato påfølges hvis tom plus en dag er lik fom`() {
            val datoperiode = Datoperiode(LocalDate.now(), LocalDate.now())
            val datoperiode2 = Datoperiode(LocalDate.now().plusDays(1), LocalDate.now().plusDays(1))
            val datoperiode3 = Datoperiode(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2))
            assertThat(datoperiode.overlapperEllerPåfølgesAv(datoperiode2)).isTrue
            assertThat(datoperiode.påfølgesAv(datoperiode3)).isTrue
        }
    }

    @Nested
    inner class FørsteTreffFraOgMedDato {
        @Test
        fun `dato midt i første periode`() {
            val perioder =
                listOf(
                    Datoperiode(LocalDate.now(), LocalDate.now().plusDays(10)),
                )
            val dato = LocalDate.now().plusDays(5)
            assertThat(finnFørsteTreffFraOgMedDato(perioder, dato)).isEqualTo(dato)
        }

        @Test
        fun `dato mellom to perioder`() {
            val perioder =
                listOf(
                    Datoperiode(LocalDate.now(), LocalDate.now().plusDays(10)),
                    Datoperiode(LocalDate.now().plusDays(20), LocalDate.now().plusDays(30)),
                )
            val dato = LocalDate.now().plusDays(15)
            assertThat(finnFørsteTreffFraOgMedDato(perioder, dato)).isEqualTo(perioder.last().fom)
        }

        @Test
        fun `dato før alle perioder`() {
            val perioder =
                listOf(
                    Datoperiode(LocalDate.now(), LocalDate.now().plusDays(10)),
                    Datoperiode(LocalDate.now().plusDays(20), LocalDate.now().plusDays(30)),
                )
            val dato = LocalDate.now().minusDays(10)
            assertThat(finnFørsteTreffFraOgMedDato(perioder, dato)).isEqualTo(perioder.first().fom)
        }

        @Test
        fun `dato etter alle perioder`() {
            val perioder =
                listOf(
                    Datoperiode(LocalDate.now(), LocalDate.now().plusDays(10)),
                    Datoperiode(LocalDate.now().plusDays(20), LocalDate.now().plusDays(30)),
                )
            val dato = LocalDate.now().plusDays(100)
            assertThat(finnFørsteTreffFraOgMedDato(perioder, dato)).isNull()
        }
    }

    private data class Datoperiode(
        override val fom: LocalDate,
        override val tom: LocalDate,
    ) : Periode<LocalDate>
}
