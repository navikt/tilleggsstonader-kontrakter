package no.nav.tilleggsstonader.kontrakter.klage

import java.time.LocalDateTime

data class FagsystemVedtak(
    val eksternBehandlingId: String,
    val behandlingstype: String,
    val resultat: String,
    val vedtakstidspunkt: LocalDateTime,
)
