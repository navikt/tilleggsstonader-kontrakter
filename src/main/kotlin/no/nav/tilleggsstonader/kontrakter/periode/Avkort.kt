package no.nav.tilleggsstonader.kontrakter.periode

import no.nav.tilleggsstonader.kontrakter.felles.KopierPeriode
import no.nav.tilleggsstonader.kontrakter.felles.Periode
import java.time.LocalDate

/**
 * Avkorter periode med nytt tom
 * Hvis en periode løper lengre enn nytt tom, får perioden nytt tom
 * Hvis perioden begynner etter nytt tom skal ikke perioden beholdes
 */
fun <T> T.avkortFraOgMed(maksTom: LocalDate): T?
    where T : Periode<LocalDate>, T : KopierPeriode<T> {
    return if (maksTom < this.fom) {
        null
    } else if (this.tom <= maksTom) {
        this
    } else {
        this.medPeriode(this.fom, minOf(this.tom, maksTom))
    }
}

fun <T> List<T>.avkortFraOgMed(fraOgMed: LocalDate): List<T>
    where T : Periode<LocalDate>, T : KopierPeriode<T> =
    this.mapNotNull { it.avkortFraOgMed(fraOgMed) }
