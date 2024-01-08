package no.nav.tilleggsstonader.kontrakter.journalpost

import no.nav.tilleggsstonader.kontrakter.felles.Tema

data class JournalposterForBrukerRequest(
    val brukerId: Bruker,
    val tema: List<Tema>? = null,
    val journalposttype: Journalposttype? = null,
    val journalstatus: Journalstatus?,
    val antall: Int = 200,
)
