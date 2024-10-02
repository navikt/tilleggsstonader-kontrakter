package no.nav.tilleggsstonader.kontrakter.brevmottaker

import java.util.UUID

sealed interface BrevmottakerDto {
    val id: UUID
}

data class BrevmottakerPersonDto(
    override val id: UUID,
    val personIdent: String,
    val navn: String?,
    val mottakerRolle: MottakerRolle,
) : BrevmottakerDto

data class BrevmottakerOrganisasjonDto(
    override val id: UUID,
    val organisasjonsnummer: String,
    val organisasjonsnavn: String,
    val navnHosOrganisasjon: String,
) : BrevmottakerDto

enum class MottakerRolle {
    BRUKER,
    VERGE,
    FULLMAKT,
}
