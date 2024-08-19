package no.nav.tilleggsstonader.kontrakter.arena.oppgave

import no.nav.tilleggsstonader.kontrakter.arena.sak.Målgruppe
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * @param benk settes hvis en oppgave er tildelt. Mapping av verdier gjøres i tilleggsstonader-arena
 * @param tildelt er Arena-ident til saksbehandler
 *
 * @param målgruppe eksisterer ikke på alle type oppgaver. Finnes blant annet på søknader som blitt automatisk journalførte
 */
data class ArenaOppgaveDto(
    val id: Long,
    val tittel: String,
    val kommentar: String,
    val fristFerdigstillelse: LocalDate,
    val benk: String?,
    val tildelt: String?,
    val opprettetTidspunkt: LocalDateTime,
    val målgruppe: Målgruppe?,
)
