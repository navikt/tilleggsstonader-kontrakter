package no.nav.tilleggsstonader.kontrakter.arena

data class ArenaStatusDto(
    val sak: SakStatus,
    val vedtak: VedtakStatus,
)

data class SakStatus(
    val harSaker: Boolean,
    val harÅpenSak: Boolean,
)

data class VedtakStatus(
    val harVedtak: Boolean,
    val harAktivtVedtak: Boolean,
)
