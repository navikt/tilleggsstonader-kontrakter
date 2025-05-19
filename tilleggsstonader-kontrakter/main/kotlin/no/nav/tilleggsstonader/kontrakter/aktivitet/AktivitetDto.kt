package no.nav.tilleggsstonader.kontrakter.aktivitet

import java.math.BigDecimal
import java.time.LocalDate

/**
 * @param type fra arena er ikke mappet til [TypeAktivitet] fordi [TypeAktivitet] kanskje ikke er komplett
 */
data class AktivitetArenaDto(
    val id: String,
    val fom: LocalDate?,
    val tom: LocalDate?,
    val type: String,
    val typeNavn: String,
    val status: StatusAktivitet?,
    val statusArena: String?,
    val antallDagerPerUke: Int?,
    val prosentDeltakelse: BigDecimal?,
    val erStønadsberettiget: Boolean?,
    val erUtdanning: Boolean?,
    val arrangør: String?,
    val kilde: Kilde,
)

enum class Kilde {
    ARENA,
}
