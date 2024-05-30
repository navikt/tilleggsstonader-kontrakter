package no.nav.tilleggsstonader.kontrakter.klage

import no.nav.tilleggsstonader.kontrakter.felles.Fagsystem
import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import java.time.LocalDate

data class OpprettKlagebehandlingRequest(
    val ident: String,
    val stønadstype: Stønadstype,
    val eksternFagsakId: String,
    val fagsystem: Fagsystem,
    val klageMottatt: LocalDate,
    val behandlendeEnhet: String,
)
