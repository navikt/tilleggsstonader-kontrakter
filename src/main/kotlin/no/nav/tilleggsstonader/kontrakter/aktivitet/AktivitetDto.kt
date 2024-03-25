package no.nav.tilleggsstonader.kontrakter.aktivitet

import java.time.LocalDate

/**
 * @param type fra arena er ikke mappet til [TypeAktivitet] fordi [TypeAktivitet] ikke er komplett
 */
data class AktivitetArenaDto(
    val id: String,
    val fom: LocalDate?,
    val tom: LocalDate?,
    val type: String,
    val typeNavn: String,
    val status: StatusAktivitet?,
    val antallDagerPerUke: Int?,
    val prosentDeltakelse: Float?,
    val arrangør: String?,
    val kilde: Kilde,
)

enum class Kilde {
    ARENA,
}
