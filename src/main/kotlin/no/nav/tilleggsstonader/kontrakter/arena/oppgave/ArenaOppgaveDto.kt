package no.nav.tilleggsstonader.kontrakter.arena.oppgave

import java.time.LocalDateTime

/**
 * @param benk settes hvis en oppgave er tildelt. Mapping av verdier gjøres i tilleggsstonader-arena
 * @param tildelt er Arena-ident til saksbehandler
 */
data class ArenaOppgaveDto(
    val id: Long,
    val tittel: String,
    val kommentar: String,
    val benk: String?,
    val tildelt: String?,
    val opprettetTidspunkt: LocalDateTime,
)
