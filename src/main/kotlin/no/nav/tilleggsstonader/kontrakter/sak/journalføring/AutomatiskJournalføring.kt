package no.nav.tilleggsstonader.kontrakter.sak.journalføring

import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import no.nav.tilleggsstonader.kontrakter.oppgave.OppgavePrioritet
import java.util.UUID

data class AutomatiskJournalføringRequest(
    val personIdent: String,
    val journalpostId: String,
    val stønadstype: Stønadstype,
    val mappeId: Long?,
    val prioritet: OppgavePrioritet = OppgavePrioritet.NORM,
)

data class AutomatiskJournalføringResponse(
    val fagsakId: UUID,
    val behandlingId: UUID,
)
