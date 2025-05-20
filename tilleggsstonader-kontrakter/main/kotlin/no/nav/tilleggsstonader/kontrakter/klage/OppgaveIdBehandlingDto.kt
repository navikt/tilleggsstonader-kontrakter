package no.nav.tilleggsstonader.kontrakter.klage

import java.util.UUID

/**
 * @param oppgaver behandlingId på oppgaveId for å kunne lenke til behandlingen fra oppgavebenken i sak
 */
data class OppgaverBehandlingerResponse(
    val oppgaver: Map<Long, UUID>,
)

data class OppgaverBehandlingerRequest(
    val oppgaveIder: List<Long>,
)
