package no.nav.tilleggsstonader.kontrakter.journalpost

import no.nav.tilleggsstonader.kontrakter.felles.Tema

data class JournalposterForBrukerRequest(
    val brukerId: Bruker,
    val tema: List<Tema> = emptyList(),
    val journalposttype: List<Journalposttype> = emptyList(),
    val journalstatus: List<Journalstatus> = emptyList(),
    val antall: Int = 200,
)
