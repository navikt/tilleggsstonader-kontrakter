package no.nav.tilleggsstonader.kontrakter.målgruppe

import java.time.LocalDate

data class MålgruppeArenaDto(
    val fom: LocalDate,
    val tom: LocalDate,
    val type: TypeMålgruppe,
    val arenaType: String,
    val arenaTypeNavn: String,
)

enum class TypeMålgruppe {
    NEDSATT_ARBEIDSEVNE,
    ENSLIG_FORSØRGER,
    GJENLEVENDE_EKTEFELLE,
    TILTAKSPENGER,
    ARBEIDSSØKER,
    DAGPENGER,

    TIDLIGERE_FAMILIEPLEIER,
}
