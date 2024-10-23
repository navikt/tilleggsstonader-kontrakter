package no.nav.tilleggsstonader.kontrakter.oppgave

import no.nav.tilleggsstonader.kontrakter.felles.Tema
import java.time.LocalDate

data class OpprettOppgaveRequest(
    val ident: OppgaveIdentV2?,
    val enhetsnummer: String?,
    val journalpostId: String? = null,
    val tema: Tema,
    val oppgavetype: Oppgavetype,
    val behandlingstema: String?,
    val tilordnetRessurs: String? = null,
    val fristFerdigstillelse: LocalDate,
    val aktivFra: LocalDate = LocalDate.now(),
    val beskrivelse: String,
    val prioritet: OppgavePrioritet = OppgavePrioritet.NORM,
    val behandlingstype: String? = null,
    val behandlesAvApplikasjon: String? = null,
    val mappeId: Long? = null,
    val saksreferanse: String? = null,
)
