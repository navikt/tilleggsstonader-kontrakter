package no.nav.tilleggsstonader.kontrakter.periode

import no.nav.tilleggsstonader.kontrakter.felles.KopierPeriode
import no.nav.tilleggsstonader.kontrakter.felles.Periode
import java.time.LocalDate

/**
 * Avkorter perioder før [minFom]
 * Hvis en periode er løper før minFom får den nytt fom
 * Hvis perioden slutter før [minFom] skal ikke perioden beholdes
 */
fun <T> T.avkortPerioderFør(minFom: LocalDate?): T?
        where T : Periode<LocalDate>, T : KopierPeriode<T> =
    if (minFom == null) {
        this
    } else if (this.tom < minFom) {
        null
    } else if (this.fom >= minFom) {
        this
    } else {
        this.medPeriode(maxOf(this.fom, minFom), this.tom)
    }

fun <T> List<T>.avkortPerioderFør(fraOgMed: LocalDate?): List<T> where T : Periode<LocalDate>, T : KopierPeriode<T> =
    this.mapNotNull { it.avkortPerioderFør(fraOgMed) }

/**
 * Avkorter perioder etter [maksTom]
 * Hvis en periode løper lengre enn nytt tom, får perioden nytt tom
 * Hvis perioden begynner etter nytt tom skal ikke perioden beholdes
 */
fun <T> T.avkortFraOgMed(maksTom: LocalDate): T?
        where T : Periode<LocalDate>, T : KopierPeriode<T> =
    if (maksTom < this.fom) {
        null
    } else if (this.tom <= maksTom) {
        this
    } else {
        this.medPeriode(this.fom, minOf(this.tom, maksTom))
    }

fun <T> List<T>.avkortFraOgMed(maksTom: LocalDate): List<T> where T : Periode<LocalDate>, T : KopierPeriode<T> =
    this.mapNotNull { it.avkortFraOgMed(maksTom) }

fun <T> List<T>.avkortFraOgMed(
    maksTom: LocalDate,
    medTom: (periode: T, nyttTom: LocalDate) -> T,
): AvkortResult<T>
        where T : Periode<LocalDate> {
    var harSplittetPeriode = false

    val avkortetListe =
        this.mapNotNull {
            if (it.tom <= maksTom) {
                it
            } else if (maksTom in it.fom..it.tom) {
                harSplittetPeriode = true
                medTom(it, minOf(it.tom, maksTom))
            } else {
                null
            }
        }
    return AvkortResult(
        perioder = avkortetListe,
        harAvkortetPeriode = harSplittetPeriode,
    )
}

data class AvkortResult<T>(
    val perioder: List<T>,
    val harAvkortetPeriode: Boolean,
)
