package no.nav.tilleggsstonader.kontrakter.felles

import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.Temporal

interface Periode<T> : Comparable<Periode<T>> where T : Comparable<T>, T : Temporal {

    val fom: T
    val tom: T
    fun validatePeriode() {
        require(tom >= fom) { "Til-og-med før fra-og-med: $fom > $tom" }
    }

    fun overlapper(other: Periode<T>): Boolean {
        return !(this.tom < other.fom || this.fom > other.tom)
    }

    fun inneholder(other: Periode<T>): Boolean {
        return this.fom <= other.fom && this.tom >= other.tom
    }

    override fun compareTo(other: Periode<T>): Int {
        return Comparator.comparing(Periode<T>::fom).thenComparing(Periode<T>::tom).compare(this, other)
    }
}

fun <T> List<Periode<T>>.erSortert(): Boolean where T : Comparable<T>, T : Temporal {
    return zipWithNext().all { it.first <= it.second }
}

fun <T> List<Periode<T>>.overlapper(): Boolean where T : Comparable<T>, T : Temporal {
    return førsteOverlappendePeriode() != null
}

fun <T> List<Periode<T>>.førsteOverlappendePeriode(): Pair<Periode<T>, Periode<T>>? where T : Comparable<T>, T : Temporal =
    this.sortedBy { it.fom }.zipWithNext().firstOrNull { it.first.overlapper(it.second) }

/**
 * Interface for å slå sammen 2 perioder
 */
interface Mergeable<R, T : Periode<R>> where R : Comparable<R>, R : Temporal {
    fun merge(other: T): T
}

fun Periode<LocalDate>.påfølgesAv(other: Periode<LocalDate>) = this.tom.plusDays(1) == other.fom
fun Periode<LocalDate>.overlapperEllerPåfølgesAv(other: Periode<LocalDate>) =
    this.overlapper(other) || this.tom.plusDays(1) == other.fom

/**
 * Forventer at perioder er sorterte når man slår de sammen
 */
fun <T, P> List<P>.mergeSammenhengende(skalMerges: (P, P) -> Boolean): List<P>
    where P : Periode<T>, T : Comparable<T>, T : Temporal, P : Mergeable<T, P> {
    return mergeSammenhengende(skalMerges) { p1, p2 -> p1.merge(p2) }
}

fun <T, P> List<P>.mergeSammenhengende(skalMerges: (P, P) -> Boolean, merge: (P, P) -> P): List<P>
    where P : Periode<T>, T : Comparable<T>, T : Temporal {
    return this.fold(mutableListOf()) { acc, entry ->
        val last = acc.lastOrNull()
        if (last != null && skalMerges(last, entry)) {
            acc.removeLast()
            acc.add(merge(last, entry))
        } else {
            acc.add(entry)
        }
        acc
    }
}

/**
 * Splitter en datoperiode till verdi per måned,
 * eks 05.01.2023 - 08.02.2023 blir listOf(Pair(jan, verdi), Pair(feb, verdi))
 */
fun <P : Periode<LocalDate>, VAL> P.splitPerMåned(value: (måned: YearMonth, periode: P) -> VAL): List<Pair<YearMonth, VAL>> {
    val perioder = mutableListOf<Pair<YearMonth, VAL>>()
    var dato = fom
    while (dato <= this.tom) {
        val årMåned = YearMonth.from(dato)
        val verdi = value(årMåned, this)
        perioder.add(Pair(årMåned, verdi))
        dato = årMåned.atEndOfMonth().plusDays(1)
    }
    return perioder
}

/**
 * Splitter en månedsperiode till verdi per måned,
 * eks 01.2023 - 02.2023 blir listOf(Pair(jan, verdi), Pair(feb, verdi))
 */
@JvmName("yearMonthSplitPerMåned")
fun <P : Periode<YearMonth>, VAL> P.splitPerMåned(value: (måned: YearMonth, periode: P) -> VAL): List<Pair<YearMonth, VAL>> {
    val perioder = mutableListOf<Pair<YearMonth, VAL>>()
    var dato = fom
    while (dato <= this.tom) {
        val verdi = value(dato, this)
        perioder.add(Pair(dato, verdi))
        dato = dato.plusMonths(1)
    }
    return perioder
}

/**
 * Returnere alle datoer i en periode.
 * Mulig denne burde være en lazy property (LazyDelegate)
 */
fun Periode<LocalDate>.alleDatoer(): List<LocalDate> {
    val perioder = mutableListOf<LocalDate>()
    var dato = fom
    while (dato <= this.tom) {
        perioder.add(dato)
        dato = dato.plusDays(1)
    }
    return perioder
}
