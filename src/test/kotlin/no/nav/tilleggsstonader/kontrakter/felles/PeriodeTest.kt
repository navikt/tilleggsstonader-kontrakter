package no.nav.tilleggsstonader.kontrakter.felles

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.YearMonth

internal class PeriodeTest {
    val jan = YearMonth.of(2023, 1)
    val feb = YearMonth.of(2023, 2)
    val mars = YearMonth.of(2023, 3)
    val april = YearMonth.of(2023, 4)
    val mai = YearMonth.of(2023, 5)

    @Nested
    inner class ErSortert {
        @Test
        fun `kun en periode er sortert`() {
            val periode1 = Månedsperiode(jan, mars)
            assertThat(listOf(periode1).erSortert()).isTrue
        }

        @Test
        fun `periode 2 starter etter periode 2`() {
            val periode1 = Månedsperiode(jan, jan)
            val periode2 = Månedsperiode(feb, feb)
            assertThat(listOf(periode1, periode2).erSortert()).isTrue
        }

        @Test
        fun `periode 2 starter før periode 1`() {
            val periode1 = Månedsperiode(feb, feb)
            val periode2 = Månedsperiode(jan, jan)
            assertThat(listOf(periode1, periode2).erSortert()).isFalse
        }
    }

    @Nested
    inner class Overlapper {
        @Test
        fun `periode 2 starter i samme måned som periode 1 slutter`() {
            val periode1 = Månedsperiode(jan, mars)
            val periode2 = Månedsperiode(mars, april)
            assertThat(listOf(periode1, periode2).overlapper()).isTrue
        }

        @Test
        fun `periode 2 starter i midten på periode 1`() {
            val periode1 = Månedsperiode(jan, mars)
            val periode2 = Månedsperiode(feb, april)
            assertThat(listOf(periode1, periode2).overlapper()).isTrue
        }

        @Test
        fun `periode 2 er en del av periode 1`() {
            val periode1 = Månedsperiode(jan, mars)
            val periode2 = Månedsperiode(feb, feb)
            assertThat(listOf(periode1, periode2).overlapper()).isTrue
        }

        @Test
        fun `periode 1 starter før periode 2 og slutter etter`() {
            val periode1 = Månedsperiode(jan, mars)
            val periode2 = Månedsperiode(feb, feb)
            assertThat(listOf(periode1, periode2).overlapper()).isTrue
        }

        @Test
        fun `periode 1 starter etter periode 2 og slutter flr`() {
            val periode1 = Månedsperiode(feb, feb)
            val periode2 = Månedsperiode(jan, mars)
            assertThat(listOf(periode1, periode2).overlapper()).isTrue
        }

        @Test
        fun `periode 2 er etter periode 1`() {
            val periode1 = Månedsperiode(jan, feb)
            val periode2 = Månedsperiode(mars, april)
            assertThat(listOf(periode1, periode2).overlapper()).isFalse
        }

        @Test
        fun `periode 2 er før periode 1`() {
            val periode1 = Månedsperiode(feb, feb)
            val periode2 = Månedsperiode(jan, jan)
            assertThat(listOf(periode1, periode2).overlapper()).isFalse
        }
    }

    @Nested
    inner class Inneholder {
        @Test
        fun `periode 2 stater etter fom og slutter før fom`() {
            val periode1 = Månedsperiode(jan, mars)
            val periode2 = Månedsperiode(feb, feb)
            assertThat(periode1.inneholder(periode2)).isTrue
            assertThat(periode2.inneholder(periode1)).isFalse
        }

        @Test
        fun `periode 2 stater samme måled som periode 1 og slutter samme måned som periode 1`() {
            val periode1 = Månedsperiode(jan, mars)
            assertThat(periode1.inneholder(periode1)).isTrue
        }

        @Test
        fun `periode 2 starter før periode 1`() {
            val periode1 = Månedsperiode(feb, mars)
            val periode2 = Månedsperiode(jan, feb)
            assertThat(periode1.inneholder(periode2)).isFalse()
            assertThat(periode2.inneholder(periode1)).isFalse()
        }

        @Test
        fun `periode 2 slutter etter periode 1`() {
            val periode1 = Månedsperiode(jan, feb)
            val periode2 = Månedsperiode(feb, mars)
            assertThat(periode1.inneholder(periode2)).isFalse()
            assertThat(periode2.inneholder(periode1)).isFalse()
        }
    }

    @Nested
    inner class MergeSammenhengende {
        private val periodeJan = Månedsperiode(jan, jan, 1)
        private val periodeFeb = Månedsperiode(feb, feb, 1)
        private val periodeMars = Månedsperiode(mars, mars, 2)
        private val periodeMai = Månedsperiode(mai, mai, 2)

        @Test
        fun `skal slå sammen perioder som er sammenhengende og har samme verdi`() {
            assertThat(listOf(periodeJan, periodeFeb).mergeSammenhengende(::skalMerges))
                .containsExactly(periodeJan.copy(tom = feb))
        }

        @Test
        fun `perioder med ulikt beløp skal ikke slås sammen`() {
            assertThat(listOf(periodeFeb, periodeMars).mergeSammenhengende(::skalMerges))
                .containsExactly(periodeFeb, periodeMars)
        }

        @Test
        fun `perioder som ikke er sammenhengende skal ikke slås sammen`() {
            assertThat(listOf(periodeMars, periodeMai).mergeSammenhengende(::skalMerges))
                .containsExactly(periodeMars, periodeMai)
        }

        private fun skalMerges(
            m: Månedsperiode,
            m2: Månedsperiode,
        ) = m.tom.plusMonths(1) == m2.fom && m.verdi == m2.verdi
    }

    @Nested
    inner class SplitPerMåned {
        private val verdi = 1

        private val datoperiode = Datoperiode(jan.atDay(5), feb.atEndOfMonth(), verdi)

        private val månedsperiode1 = Månedsperiode(jan, jan, verdi)
        private val månedsperiode2 = Månedsperiode(jan, mars, 10)

        @Test
        fun `skal splitte datoperiode per måned`() {
            assertThat(datoperiode.splitPerMåned { _, _ -> verdi })
                .containsExactly(Pair(jan, verdi), Pair(feb, verdi))
        }

        @Test
        fun `skal splitte månedsperiode per måned`() {
            assertThat(månedsperiode1.splitPerMåned { _, _ -> verdi })
                .containsExactly(Pair(jan, verdi))

            assertThat(månedsperiode2.splitPerMåned { _, _ -> verdi })
                .containsExactly(Pair(jan, verdi), Pair(feb, verdi), Pair(mars, verdi))
        }
    }

    private data class Datoperiode(
        override val fom: LocalDate,
        override val tom: LocalDate,
        val verdi: Int = 0,
    ) : Periode<LocalDate>

    private data class Månedsperiode(
        override val fom: YearMonth,
        override val tom: YearMonth,
        val verdi: Int = 0,
    ) : Periode<YearMonth>,
        Mergeable<YearMonth, Månedsperiode> {
        override fun merge(other: Månedsperiode): Månedsperiode = this.copy(tom = other.tom)
    }
}
