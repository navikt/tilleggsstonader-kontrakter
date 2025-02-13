package no.nav.tilleggsstonader.kontrakter.periode

import no.nav.tilleggsstonader.kontrakter.felles.KopierPeriode
import no.nav.tilleggsstonader.kontrakter.felles.Periode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class MergeOverlappendeKtTest {
    @Test
    fun `2 like perioder`() {
        val periode = MinPeriode(fom = LocalDate.of(2025, 1, 1), tom = LocalDate.of(2025, 1, 1))
        val result = listOf(periode, periode).mergeOverlappende()

        assertThat(result).containsExactly(periode)
    }

    @Test
    fun `periode 2 slutter etter periode 1`() {
        val result =
            listOf(
                MinPeriode(fom = LocalDate.of(2025, 1, 1), tom = LocalDate.of(2025, 1, 1)),
                MinPeriode(fom = LocalDate.of(2025, 1, 1), tom = LocalDate.of(2025, 1, 2)),
            ).mergeOverlappende()

        assertThat(result).containsExactly(
            MinPeriode(fom = LocalDate.of(2025, 1, 1), tom = LocalDate.of(2025, 1, 1)),
            MinPeriode(fom = LocalDate.of(2025, 1, 2), tom = LocalDate.of(2025, 1, 2)),
        )
    }

    @Test
    fun `periode 2 er etter periode 1`() {
        val result =
            listOf(
                MinPeriode(fom = LocalDate.of(2025, 1, 1), tom = LocalDate.of(2025, 1, 1)),
                MinPeriode(fom = LocalDate.of(2025, 1, 2), tom = LocalDate.of(2025, 1, 2)),
            ).mergeOverlappende()

        assertThat(result).containsExactly(
            MinPeriode(fom = LocalDate.of(2025, 1, 1), tom = LocalDate.of(2025, 1, 1)),
            MinPeriode(fom = LocalDate.of(2025, 1, 2), tom = LocalDate.of(2025, 1, 2)),
        )
    }

    @Test
    fun `Skal merge perioder`() {
        val result =
            listOf(
                MinPeriode(fom = LocalDate.of(2025, 1, 1), tom = LocalDate.of(2025, 1, 31), beløp = 1),
                MinPeriode(fom = LocalDate.of(2025, 1, 15), tom = LocalDate.of(2025, 1, 31), beløp = 4),
                MinPeriode(fom = LocalDate.of(2025, 1, 20), tom = LocalDate.of(2025, 1, 31), beløp = 10),
            ).mergeOverlappende()

        assertThat(result).containsExactly(
            MinPeriode(fom = LocalDate.of(2025, 1, 1), tom = LocalDate.of(2025, 1, 14), beløp = 1),
            MinPeriode(fom = LocalDate.of(2025, 1, 15), tom = LocalDate.of(2025, 1, 19), beløp = 5),
            MinPeriode(fom = LocalDate.of(2025, 1, 20), tom = LocalDate.of(2025, 1, 31), beløp = 15),
        )
    }

    @Test
    fun `skal ikke merge perioder som ikke er like`() {
        val periode1 = MinPeriode(fom = LocalDate.of(2025, 1, 1), tom = LocalDate.of(2025, 1, 1), type = 0, beløp = 1)
        val periode2 = MinPeriode(fom = LocalDate.of(2025, 1, 1), tom = LocalDate.of(2025, 1, 1), type = 1, beløp = 1)
        val result = listOf(periode1, periode2).mergeOverlappende()

        assertThat(result).containsExactly(periode1, periode2)
    }

    private fun List<MinPeriode>.mergeOverlappende() =
        mergeOverlappende(
            erLike = { periode1, periode2 -> periode1.type == periode2.type },
            merge = { periode1, periode2 -> periode1.copy(beløp = periode1.beløp + periode2.beløp) },
        )

    data class MinPeriode(
        override val fom: LocalDate,
        override val tom: LocalDate,
        val type: Int = 0,
        val beløp: Int = 0,
    ) : Periode<LocalDate>,
        KopierPeriode<MinPeriode> {
        override fun medPeriode(
            fom: LocalDate,
            tom: LocalDate,
        ): MinPeriode = this.copy(fom = fom, tom = tom)
    }
}
