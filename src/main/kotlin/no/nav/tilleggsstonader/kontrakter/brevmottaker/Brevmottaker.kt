package no.nav.tilleggsstonader.kontrakter.brevmottaker

import java.util.*

data class BrevmottakerPersonDto(
    val id: UUID,
    val personIdent: String,
    val navn: String?,
    val mottakerRolle: MottakerRolle,
)

data class BrevmottakerOrganisasjonDto(
    val id: UUID,
    val organisasjonsnummer: String,
    val organisasjonsnavn: String,
    val navnHosOrganisasjon: String,
)

enum class MottakerRolle {
    BRUKER,
    VERGE,
    FULLMAKT,
}
