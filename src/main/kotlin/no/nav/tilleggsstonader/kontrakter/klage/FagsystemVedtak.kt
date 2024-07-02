@file:Suppress("unused")

package no.nav.tilleggsstonader.kontrakter.klage

import java.time.LocalDateTime

data class FagsystemVedtak(
    val eksternBehandlingId: String,
    val behandlingstype: String,
    val resultat: String,
    val vedtakstidspunkt: LocalDateTime,
    val fagsystemType: FagsystemType,
    val regelverk: Regelverk,
)

enum class Regelverk {
    NASJONAL,
    EØS,
}

enum class FagsystemType {
    ORDNIÆR, // brukes for behandlinger fra TS-sak
    TILBAKEKREVING, // framtidig
}
