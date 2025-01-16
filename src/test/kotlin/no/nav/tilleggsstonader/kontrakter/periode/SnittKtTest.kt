package no.nav.tilleggsstonader.kontrakter.periode

import no.nav.tilleggsstonader.kontrakter.felles.Datoperiode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SnittKtTest {

    @Test
    fun `datoer er like`() {
        val datoperiode = Datoperiode(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 3))

        val snitt = datoperiode.beregnSnitt(datoperiode)!!

        assertThat(snitt.fom).isEqualTo(datoperiode.fom)
        assertThat(snitt.tom).isEqualTo(datoperiode.tom)
    }

    @Test
    fun `dato 2 overlapper i starten av periode 1`() {
        val datoperiode = Datoperiode(LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 4))
        val datoperiode2 = Datoperiode(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 3))

        val snitt = datoperiode.beregnSnitt(datoperiode2)!!

        assertThat(snitt.fom).isEqualTo(datoperiode.fom)
        assertThat(snitt.tom).isEqualTo(datoperiode2.tom)
    }

    @Test
    fun `dato 2 overlapper i sluttet av periode 1`() {
        val datoperiode = Datoperiode(LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 4))
        val datoperiode2 = Datoperiode(LocalDate.of(2025, 1, 3), LocalDate.of(2025, 1, 5))

        val snitt = datoperiode.beregnSnitt(datoperiode2)!!

        assertThat(snitt.fom).isEqualTo(datoperiode2.fom)
        assertThat(snitt.tom).isEqualTo(datoperiode.tom)
    }

    @Test
    fun `dato 2 er en del av periode 1`() {
        val datoperiode = Datoperiode(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5))
        val datoperiode2 = Datoperiode(LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 3))

        val snitt = datoperiode.beregnSnitt(datoperiode2)!!

        assertThat(snitt.fom).isEqualTo(datoperiode2.fom)
        assertThat(snitt.tom).isEqualTo(datoperiode2.tom)
    }

    @Test
    fun `periode 1 er en del av periode 2`() {
        val datoperiode = Datoperiode(LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 3))
        val datoperiode2 = Datoperiode(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5))

        val snitt = datoperiode.beregnSnitt(datoperiode2)!!

        assertThat(snitt.fom).isEqualTo(datoperiode.fom)
        assertThat(snitt.tom).isEqualTo(datoperiode.tom)
    }

    @Nested
    inner class IkkeSnitt {

        @Test
        fun `ikke snitt hvis periode 2 begynner før periode 1`() {
            val datoperiode = Datoperiode(LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 3))
            val datoPeriode2 = Datoperiode(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1))

            assertThat(datoperiode.beregnSnitt(datoPeriode2)).isNull()
        }

        @Test
        fun `ikke snitt hvis periode 2 begynner etter periode 1`() {
            val datoperiode = Datoperiode(LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 3))
            val periode2 = Datoperiode(LocalDate.of(2025, 1, 4), LocalDate.of(2025, 1, 4))

            assertThat(datoperiode.beregnSnitt(periode2)).isNull()
        }
    }
}
