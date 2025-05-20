package no.nav.tilleggsstonader.kontrakter.arena

import java.time.LocalDate

data class ArenaStatusDto(
    val sak: SakStatus,
    val vedtak: VedtakStatus,
)

data class SakStatus(
    val harAktivSakUtenVedtak: Boolean,
)

/**
 * @param harVedtak finnes vedtak med/uten utfall. Uten ufall kan være at det ikke blir innstillt ennå
 * @param harInnvilgetVedtak finnes innvilget vedtak
 * @param harAktivtVedtak har vedtak med utfall JA med tom > dagens dato
 * @param harVedtakUtenUtfall har vedtak uten utfall, dvs vedtak som ikke fått utfall ennå
 * @param vedtakTom maks-tom for vedtak med utfall JA
 */
data class VedtakStatus(
    val harVedtak: Boolean,
    val harInnvilgetVedtak: Boolean,
    val harAktivtVedtak: Boolean,
    val harVedtakUtenUtfall: Boolean,
    val vedtakTom: LocalDate? = null,
)
