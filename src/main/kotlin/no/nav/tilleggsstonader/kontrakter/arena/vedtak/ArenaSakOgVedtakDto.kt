package no.nav.tilleggsstonader.kontrakter.arena.vedtak

import java.time.LocalDate

/**
 * Inneholder vedtak og saker til en person i Arena.
 * Hvert vedtak er koblet til en sak og kan hentes fra map over saker.
 * Saker er en separat del fordi en sak kan være koblet til flere vedtak
 */
data class ArenaSakOgVedtakDto(
    val vedtak: List<VedtakDto>,
    val saker: Map<Int, SakDto>,
)

data class SakDto(
    val målgruppe: String?,
    val aktivitet: AktivitetDto?,
)

data class AktivitetDto(
    val aktivitetId: Int,
    val type: String,
    val status: String,
    val beskrivelse: String?,
    val gjelderUtdanning: Boolean,

    val typekode: String,
    val statuskode: String,
)

data class VedtakDto(
    val sakId: Int,
    val type: String,
    val status: String,
    val rettighet: String,
    val rettighetkode: Rettighet,
    val fom: LocalDate?,
    val tom: LocalDate?,
    val totalbeløp: Int?,
    val datoInnstillt: LocalDate?,
    val utfall: String?,
    val vedtakfakta: List<VedtakfaktaDto>,
)

data class VedtakfaktaDto(
    val type: String,
    val verdi: String?,
)
