package no.nav.tilleggsstonader.kontrakter.sak.journalføring

import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype

data class HåndterSøknadRequest(
    val personIdent: String,
    val journalpostId: String,
    val stønadstype: Stønadstype,
)
