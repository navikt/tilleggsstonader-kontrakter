package no.nav.tilleggsstonader.kontrakter.periode

import no.nav.tilleggsstonader.kontrakter.felles.KopierPeriode
import no.nav.tilleggsstonader.kontrakter.felles.Periode
import java.time.LocalDate

fun <T> T.beregnSnitt(other: Periode<LocalDate>): T?
    where T : Periode<LocalDate>, T : KopierPeriode<T> =
    if (this.overlapper(other)) {
        this.medPeriode(fom = maxOf(this.fom, other.fom), tom = minOf(this.tom, other.tom))
    } else {
        null
    }

fun <T, R> List<T>.beregnSnitt(
    other: List<R>,
): List<Pair<T, R>>
    where T : Periode<LocalDate>,
          T : KopierPeriode<T>,
          R : Periode<LocalDate>,
          R : KopierPeriode<R> =
    this.flatMap { t ->
        other.mapNotNull { r ->
            if (!t.overlapper(r)) return@mapNotNull null
            val snittFom = maxOf(t.fom, r.fom)
            val snittTom = minOf(t.tom, r.tom)
            t.medPeriode(snittFom, snittTom) to r.medPeriode(snittFom, snittTom)
        }
    }
