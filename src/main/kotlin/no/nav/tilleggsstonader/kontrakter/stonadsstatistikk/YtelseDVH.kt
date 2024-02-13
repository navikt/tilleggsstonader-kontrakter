package no.nav.tilleggsstonader.kontrakter.stonadsstatistikk

import java.time.ZonedDateTime

data class VedtakPassAvBarn(
    val fagsakId: Long, // Ekstern fagsakId
    val behandlingId: Long, // Ekstern behandlingId
    val relatertBehandlingId: Long? = null, // Ekstern behandlingId på relatert behandling
    val adressebeskyttelse: Adressebeskyttelse? = null,
    val tidspunktVedtak: ZonedDateTime? = null,
)


enum class Adressebeskyttelse {
    STRENGT_FORTROLIG,
    STRENGT_FORTROLIG_UTLAND,
    FORTROLIG,
    UGRADERT
}
