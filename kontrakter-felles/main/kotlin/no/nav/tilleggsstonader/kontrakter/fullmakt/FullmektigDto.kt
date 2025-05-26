package no.nav.tilleggsstonader.kontrakter.fullmakt

import java.time.LocalDate

data class FullmektigDto(
    val fullmektigIdent: String,
    val fullmektigNavn: String?,
    val gyldigFraOgMed: LocalDate,
    val gyldigTilOgMed: LocalDate?,
    val temaer: List<String>,
)
