package no.nav.tilleggsstonader.kontrakter.journalpost

import no.nav.tilleggsstonader.kontrakter.felles.Tema

data class JournalposterForBrukerRequest(
    val brukerId: Bruker,
    val antall: Int,
    val tema: List<Tema>? = null,
    val journalposttype: List<Journalposttype>? = null,
)
