package no.nav.tilleggsstonader.kontrakter.klage

import java.time.LocalDateTime

data class FagsystemVedtak(
    val eksternBehandlingId: String,
    val behandlingstype: String,
    val resultat: String,
    val vedtakstidspunkt: LocalDateTime,
    val fagsystemType: FagsystemType? = null, // TODO optional fjernes når sak og klage er oppdatert med nye kontrakter
    val regelverk: Regelverk? = null, // TODO null fjernes når sak og klage er oppdatert med nye kontraker
)

enum class Regelverk {
    NASJONAL,
    EØS,
}

enum class FagsystemType {
    ORDNIÆR, // brukes for behandlinger fra TS-sak
    TILBAKEKREVING, // framtidig
}
