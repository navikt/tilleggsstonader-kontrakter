package no.nav.tilleggsstonader.kontrakter.oppgave.vent

import java.time.LocalDate

/**
 * Requester og response mot tilleggsstonader-integrasjoner
 */
data class SettPåVentRequest(
    val oppgaveId: Long,
    val oppgaveVersjon: Int,
    val kommentar: String?,
    val frist: LocalDate,
    val beholdOppgave: Boolean,
)

data class SettPåVentResponse(
    val oppgaveId: Long,
    val oppgaveVersjon: Int,
)

data class TaAvVentRequest(
    val oppgaveId: Long,
    val oppgaveVersjon: Int,
    val kommentar: String?,
    val beholdOppgave: Boolean,
)
