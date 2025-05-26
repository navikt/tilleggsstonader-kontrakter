package no.nav.tilleggsstonader.kontrakter.dokarkiv

import no.nav.tilleggsstonader.kontrakter.felles.BrukerIdType

data class DokarkivBruker(
    val idType: BrukerIdType,
    val id: String,
)
