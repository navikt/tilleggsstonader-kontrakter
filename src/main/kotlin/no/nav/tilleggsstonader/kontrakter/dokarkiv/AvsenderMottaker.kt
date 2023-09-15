package no.nav.tilleggsstonader.kontrakter.dokarkiv

import no.nav.tilleggsstonader.kontrakter.felles.BrukerIdType

data class AvsenderMottaker(
    val id: String?,
    val idType: BrukerIdType?,
    val navn: String,
)
