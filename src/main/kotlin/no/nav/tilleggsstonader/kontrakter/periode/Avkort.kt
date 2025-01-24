package no.nav.tilleggsstonader.kontrakter.periode

import no.nav.tilleggsstonader.kontrakter.felles.KopierPeriode
import no.nav.tilleggsstonader.kontrakter.felles.Periode
import java.time.LocalDate

fun <T> T.avkortTilOgMed(fraOgMed: LocalDate): T?
    where T : Periode<LocalDate>, T : KopierPeriode<T> {
    return if (fraOgMed < this.fom) {
        null
    } else if (this.tom <= fraOgMed) {
        this
    } else {
        this.medPeriode(this.fom, minOf(this.tom, fraOgMed))
    }
}

fun <T> List<T>.avkortTilOgMed(fraOgMed: LocalDate): List<T>
    where T : Periode<LocalDate>, T : KopierPeriode<T> =
    this.mapNotNull { it.avkortTilOgMed(fraOgMed) }
