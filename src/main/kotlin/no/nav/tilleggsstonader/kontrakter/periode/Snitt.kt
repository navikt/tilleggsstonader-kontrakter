package no.nav.tilleggsstonader.kontrakter.periode

import no.nav.tilleggsstonader.kontrakter.felles.KopierPeriode
import no.nav.tilleggsstonader.kontrakter.felles.Periode
import java.time.LocalDate

fun <T> T.beregnSnitt(other: Periode<LocalDate>): T?
    where T : Periode<LocalDate>, T : KopierPeriode<T> {
    return if (this.overlapper(other)) {
        this.medPeriode(fom = maxOf(this.fom, other.fom), tom = minOf(this.tom, other.tom))
    } else {
        return null
    }
}
