package no.nav.tilleggsstonader.kontrakter.periode

import no.nav.tilleggsstonader.kontrakter.felles.KopierPeriode
import no.nav.tilleggsstonader.kontrakter.felles.Periode
import java.time.LocalDate

/**
 * @param erLike skal kun slå sammen overlappende hvis overlappende OG [erLike]
 * @param merge slår sammen 2 perioder. Trenger ikke å gjøre noe med datoene, då [periode1Snitt] allerede er snittet av de 2 periodene
 * Vanligt usecase: {p1, p2 -> p1.copy(beløp = p1.beløp + p2.beløp)}
 *
 * @return alle perioder, men merger de periodene som overlapper.
 * Deler av periodene som ikke overlapper blir avkortet og beholdt som egne perioder
 * Dvs 1jan til 2jan og 2jan til 3jan blir
 * 1jan til 1jan
 * 2jan til 2jan // overlappende periode - merges, sånn at eks beløp kan slåes sammen for denne perioden
 * 3jan til 3jan
 */
fun <T> List<T>.mergeOverlappende(
    erLike: (periode1: T, periode2: T) -> Boolean,
    merge: (periode1Snitt: T, periode2: T) -> T,
): List<T> where T : Periode<LocalDate>, T : KopierPeriode<T> =
    this
        .sortedWith(compareBy<T> { it.fom }.thenBy { it.tom })
        .fold(listOf()) { acc, entry ->
            if (acc.isEmpty()) {
                return@fold listOf(entry)
            }
            val (overlapper, overlapperIkke) = acc.partition { it.overlapper(entry) && erLike(it, entry) }

            if (overlapper.isEmpty()) {
                return@fold overlapperIkke + entry
            }

            overlapperIkke +
                overlapper.flatMap {
                    val førSnitt = it.avkortFraOgMed(entry.fom.minusDays(1))
                    val snitt = it.beregnSnitt(entry)?.let { merge(it, entry) }
                    val etterSnitt = entry.avkortPerioderFør(it.tom.plusDays(1))
                    listOfNotNull(førSnitt, snitt, etterSnitt)
                }
        }
