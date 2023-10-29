package no.nav.tilleggsstonader.kontrakter.sak.journalføring

import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import java.util.UUID

data class AutomatiskJournalføringRequest(
    val personIdent: String,
    val journalpostId: String,
    val stønadstype: Stønadstype,
)

data class AutomatiskJournalføringResponse(
    val fagsakId: UUID,
    val behandlingId: UUID?,
)
