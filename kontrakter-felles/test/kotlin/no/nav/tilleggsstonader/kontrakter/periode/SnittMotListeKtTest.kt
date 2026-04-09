package no.nav.tilleggsstonader.kontrakter.periode

import no.nav.tilleggsstonader.kontrakter.felles.KopierPeriode
import no.nav.tilleggsstonader.kontrakter.felles.Periode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SnittMotListeKtTest {
    // Hjelpefunksjon for å lage LocalDate mer lesbart i testene
    private fun dato(
        year: Int,
        month: Int,
        day: Int,
    ) = LocalDate.of(year, month, day)

    data class PeriodeA(
        override val fom: LocalDate,
        override val tom: LocalDate,
    ) : Periode<LocalDate>,
        KopierPeriode<PeriodeA> {
        override fun medPeriode(
            fom: LocalDate,
            tom: LocalDate,
        ): PeriodeA = copy(fom = fom, tom = tom)
    }

    data class PeriodeB(
        override val fom: LocalDate,
        override val tom: LocalDate,
    ) : Periode<LocalDate>,
        KopierPeriode<PeriodeB> {
        override fun medPeriode(
            fom: LocalDate,
            tom: LocalDate,
        ): PeriodeB = copy(fom = fom, tom = tom)
    }

    @Test
    fun `eksempel fra krav - én A-periode splittet av to B-perioder`() {
        val liste1 = listOf(PeriodeA(dato(2025, 12, 1), dato(2026, 1, 31)))
        val liste2 =
            listOf(
                PeriodeB(dato(2025, 12, 1), dato(2025, 12, 31)),
                PeriodeB(dato(2026, 1, 1), dato(2026, 1, 31)),
            )

        val resultat = liste1.beregnSnitt(liste2)

        assertThat(resultat).containsExactly(
            PeriodeA(dato(2025, 12, 1), dato(2025, 12, 31)) to PeriodeB(dato(2025, 12, 1), dato(2025, 12, 31)),
            PeriodeA(dato(2026, 1, 1), dato(2026, 1, 31)) to PeriodeB(dato(2026, 1, 1), dato(2026, 1, 31)),
        )
    }

    @Test
    fun `like perioder gir snitt lik perioden`() {
        val a = PeriodeA(dato(2025, 1, 1), dato(2025, 1, 31))
        val b = PeriodeB(dato(2025, 1, 1), dato(2025, 1, 31))

        val resultat = listOf(a).beregnSnitt(listOf(b))

        assertThat(resultat).containsExactly(a to b)
    }

    @Test
    fun `B overlapper starten av A`() {
        val a = PeriodeA(dato(2025, 1, 10), dato(2025, 1, 31))
        val b = PeriodeB(dato(2025, 1, 1), dato(2025, 1, 15))

        val resultat = listOf(a).beregnSnitt(listOf(b))

        assertThat(resultat).containsExactly(
            PeriodeA(dato(2025, 1, 10), dato(2025, 1, 15)) to PeriodeB(dato(2025, 1, 10), dato(2025, 1, 15)),
        )
    }

    @Test
    fun `B overlapper slutten av A`() {
        val a = PeriodeA(dato(2025, 1, 1), dato(2025, 1, 20))
        val b = PeriodeB(dato(2025, 1, 15), dato(2025, 1, 31))

        val resultat = listOf(a).beregnSnitt(listOf(b))

        assertThat(resultat).containsExactly(
            PeriodeA(dato(2025, 1, 15), dato(2025, 1, 20)) to PeriodeB(dato(2025, 1, 15), dato(2025, 1, 20)),
        )
    }

    @Test
    fun `B er en del av A`() {
        val a = PeriodeA(dato(2025, 1, 1), dato(2025, 1, 31))
        val b = PeriodeB(dato(2025, 1, 10), dato(2025, 1, 20))

        val resultat = listOf(a).beregnSnitt(listOf(b))

        assertThat(resultat).containsExactly(
            PeriodeA(dato(2025, 1, 10), dato(2025, 1, 20)) to PeriodeB(dato(2025, 1, 10), dato(2025, 1, 20)),
        )
    }

    @Test
    fun `A er en del av B`() {
        val a = PeriodeA(dato(2025, 1, 10), dato(2025, 1, 20))
        val b = PeriodeB(dato(2025, 1, 1), dato(2025, 1, 31))

        val resultat = listOf(a).beregnSnitt(listOf(b))

        assertThat(resultat).containsExactly(
            PeriodeA(dato(2025, 1, 10), dato(2025, 1, 20)) to PeriodeB(dato(2025, 1, 10), dato(2025, 1, 20)),
        )
    }

    @Test
    fun `kun én dag overlapper`() {
        val a = PeriodeA(dato(2025, 1, 1), dato(2025, 1, 15))
        val b = PeriodeB(dato(2025, 1, 15), dato(2025, 1, 31))

        val resultat = listOf(a).beregnSnitt(listOf(b))

        assertThat(resultat).containsExactly(
            PeriodeA(dato(2025, 1, 15), dato(2025, 1, 15)) to PeriodeB(dato(2025, 1, 15), dato(2025, 1, 15)),
        )
    }

    @Test
    fun `flere A-perioder mot én B-periode`() {
        val liste1 =
            listOf(
                PeriodeA(dato(2025, 1, 1), dato(2025, 1, 10)),
                PeriodeA(dato(2025, 1, 20), dato(2025, 1, 31)),
            )
        val b = PeriodeB(dato(2025, 1, 5), dato(2025, 1, 25))

        val resultat = liste1.beregnSnitt(listOf(b))

        assertThat(resultat).containsExactly(
            PeriodeA(dato(2025, 1, 5), dato(2025, 1, 10)) to PeriodeB(dato(2025, 1, 5), dato(2025, 1, 10)),
            PeriodeA(dato(2025, 1, 20), dato(2025, 1, 25)) to PeriodeB(dato(2025, 1, 20), dato(2025, 1, 25)),
        )
    }

    @Test
    fun `én A-periode mot flere B-perioder med gap mellom`() {
        val a = PeriodeA(dato(2025, 1, 1), dato(2025, 1, 31))
        val liste2 =
            listOf(
                PeriodeB(dato(2025, 1, 1), dato(2025, 1, 10)),
                PeriodeB(dato(2025, 1, 20), dato(2025, 1, 31)),
            )

        val resultat = listOf(a).beregnSnitt(liste2)

        assertThat(resultat).containsExactly(
            PeriodeA(dato(2025, 1, 1), dato(2025, 1, 10)) to PeriodeB(dato(2025, 1, 1), dato(2025, 1, 10)),
            PeriodeA(dato(2025, 1, 20), dato(2025, 1, 31)) to PeriodeB(dato(2025, 1, 20), dato(2025, 1, 31)),
        )
    }

    @Test
    fun `flere A-perioder mot flere B-perioder`() {
        val liste1 =
            listOf(
                PeriodeA(dato(2025, 1, 1), dato(2025, 1, 15)),
                PeriodeA(dato(2025, 2, 1), dato(2025, 2, 28)),
            )
        val liste2 =
            listOf(
                PeriodeB(dato(2025, 1, 10), dato(2025, 1, 20)),
                PeriodeB(dato(2025, 2, 10), dato(2025, 3, 10)),
            )

        val resultat = liste1.beregnSnitt(liste2)

        assertThat(resultat).containsExactly(
            PeriodeA(dato(2025, 1, 10), dato(2025, 1, 15)) to PeriodeB(dato(2025, 1, 10), dato(2025, 1, 15)),
            PeriodeA(dato(2025, 2, 10), dato(2025, 2, 28)) to PeriodeB(dato(2025, 2, 10), dato(2025, 2, 28)),
        )
    }

    @Nested
    inner class IngenSnitt {
        @Test
        fun `ingen snitt når B slutter før A begynner`() {
            val a = PeriodeA(dato(2025, 2, 1), dato(2025, 2, 28))
            val b = PeriodeB(dato(2025, 1, 1), dato(2025, 1, 31))

            assertThat(listOf(a).beregnSnitt(listOf(b))).isEmpty()
        }

        @Test
        fun `ingen snitt når B begynner etter A slutter`() {
            val a = PeriodeA(dato(2025, 1, 1), dato(2025, 1, 31))
            val b = PeriodeB(dato(2025, 2, 1), dato(2025, 2, 28))

            assertThat(listOf(a).beregnSnitt(listOf(b))).isEmpty()
        }

        @Test
        fun `tom liste1 gir tomt resultat`() {
            val b = PeriodeB(dato(2025, 1, 1), dato(2025, 1, 31))

            assertThat(emptyList<PeriodeA>().beregnSnitt(listOf(b))).isEmpty()
        }

        @Test
        fun `tom liste2 gir tomt resultat`() {
            val a = PeriodeA(dato(2025, 1, 1), dato(2025, 1, 31))

            assertThat(listOf(a).beregnSnitt(emptyList<PeriodeB>())).isEmpty()
        }

        @Test
        fun `begge lister tomme gir tomt resultat`() {
            assertThat(emptyList<PeriodeA>().beregnSnitt(emptyList<PeriodeB>())).isEmpty()
        }

        @Test
        fun `ingen snitt når alle B-perioder er utenfor A`() {
            val a = PeriodeA(dato(2025, 3, 1), dato(2025, 3, 31))
            val liste2 =
                listOf(
                    PeriodeB(dato(2025, 1, 1), dato(2025, 1, 31)),
                    PeriodeB(dato(2025, 5, 1), dato(2025, 5, 31)),
                )

            assertThat(listOf(a).beregnSnitt(liste2)).isEmpty()
        }
    }
}
