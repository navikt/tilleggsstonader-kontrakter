package no.nav.tilleggsstonader.kontrakter.oppgave

import no.nav.tilleggsstonader.kontrakter.felles.Tema
import java.time.LocalDate

@JvmInline
value class PersonIdent(
    val ident: String,
) {
    init {
        if (ident.length !in 11..13) {
            error("Personident må være mellom 11 og 13 tegn")
        }
    }
}

data class OpprettOppgaveRequest(
    val personident: PersonIdent? = null,
    @Deprecated("Skal ikke brukes, foretrekk personident")
    val ident: OppgaveIdentV2? = null,
    val tema: Tema,
    val oppgavetype: Oppgavetype,
    val prioritet: OppgavePrioritet = OppgavePrioritet.NORM,
    val aktivDato: LocalDate = LocalDate.now(),
    val beskrivelse: String? = null,
    val enhetsnummer: String? = null,
    val journalpostId: String? = null,
    val behandlingstema: String?,
    val tilordnetRessurs: String? = null,
    val fristFerdigstillelse: LocalDate,
    val behandlingstype: String? = null,
    val behandlesAvApplikasjon: String? = null,
    val mappeId: Long? = null,
    val saksreferanse: String? = null,
) {
    init {
        require(ident != null || personident != null) {
            "Enten ident eller personident må være satt"
        }
    }
}
