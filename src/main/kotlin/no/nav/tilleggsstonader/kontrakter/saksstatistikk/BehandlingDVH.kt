package no.nav.tilleggsstonader.kontrakter.saksstatistikk

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.ZonedDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BehandlingDVH (
    val behandlingId: Long, // Fagsystemets eksterne behandlings-ID
    val personIdent: String, // PersonIdent tilknyttet søker eller hovedaktør for ytelsen
    val registrertTid: ZonedDateTime, // Tidspunkt da behandlingen først oppstod eller ble registrert i fagsystemet
    val endretTid: ZonedDateTime, // Tidspunkt for siste endring på behandlingen. Ved første melding vil denne være lik registrertTid
    val tekniskTid: ZonedDateTime, // Tidspunktet da fagsystemet legger hendelsen på grensesnittet/topicen
    val sakYtelse: String, // Kode som angir hvilken ytelse/stønad behandlingen gjelder
    val behandlingStatus: String, // Kode som angir hvilken status behandlingen har - typisk: opprettet, under behandling, avsluttet, etc
)