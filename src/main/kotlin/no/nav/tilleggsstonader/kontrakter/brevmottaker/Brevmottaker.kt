package no.nav.tilleggsstonader.kontrakter.brevmottaker

import java.util.*

data class BrevmottakerPerson(
    val id: UUID,
    val personIdent: String,
    val navn: String,
    val mottakerRolle: MottakerRolle,
)

data class BrevmottakerOrganisasjon(
    val id: UUID,
    val organisasjonsnummer: String,
    val organisasjonsnavn: String,
    val navnHosOrganisasjon: String,
    val mottakerRolle: MottakerRolle,
)

enum class MottakerRolle {
    BRUKER,
    VERGE,
    FULLMAKT,
}
