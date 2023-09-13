package no.nav.tilleggsstonader.kontrakter.oppgave

import no.nav.tilleggsstonader.kontrakter.felles.Behandlingstema
import no.nav.tilleggsstonader.kontrakter.felles.Tema
import java.time.LocalDate
import java.time.LocalDateTime

data class FinnOppgaveResponseDto(
    val antallTreffTotalt: Long,
    val oppgaver: List<Oppgave>,
)

/**
 * [enhetsmappe] finnes, men har ulike verdier i ulike miljøer
 */
data class FinnOppgaveRequest(
    val tema: Tema?,
    val behandlingstema: Behandlingstema? = null,
    val behandlingstype: Behandlingstype? = null,
    val erUtenMappe: Boolean? = null,
    val oppgavetype: Oppgavetype? = null,
    val enhet: String? = null,
    val saksbehandler: String? = null,
    val aktørId: String? = null,
    val journalpostId: String? = null,
    val saksreferanse: String? = null,
    val tilordnetRessurs: String? = null,
    val tildeltRessurs: Boolean? = null,
    val opprettetFomTidspunkt: LocalDateTime? = null,
    val opprettetTomTidspunkt: LocalDateTime? = null,
    val fristFomDato: LocalDate? = null,
    val fristTomDato: LocalDate? = null,
    val aktivFomDato: LocalDate? = null,
    val aktivTomDato: LocalDate? = null,
    val mappeId: Long? = null,
    val limit: Long? = null,
    val offset: Long? = null,
)