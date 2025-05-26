package no.nav.tilleggsstonader.kontrakter.dokarkiv

import com.fasterxml.jackson.annotation.JsonInclude
import no.nav.tilleggsstonader.kontrakter.felles.Behandlingstema
import no.nav.tilleggsstonader.kontrakter.felles.Fagsystem
import no.nav.tilleggsstonader.kontrakter.felles.Tema

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OppdaterJournalpostRequest(
    val avsenderMottaker: AvsenderMottaker? = null,
    val bruker: DokarkivBruker? = null,
    val tema: Tema? = null,
    val behandlingstema: Behandlingstema? = null,
    val tittel: String? = null,
    val journalfoerendeEnhet: String? = null,
    val sak: Sak? = null,
    val dokumenter: List<DokumentInfo>? = null,
)

data class Sak(
    val arkivsaksnummer: String? = null,
    val arkivsaksystem: String? = null,
    val fagsakId: String? = null,
    val fagsaksystem: Fagsystem? = null,
    val sakstype: String? = null,
)
