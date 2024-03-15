package no.nav.tilleggsstonader.kontrakter.aktivitet

import java.time.LocalDate

data class AktivitetDto(
    val id: String,
    val fom: LocalDate?,
    val tom: LocalDate?,
    val type: TypeAktivitet,
    val status: StatusAktivitet?,
    val antallDagerPerUke: Int?,
    val prosentDeltakelse: Float?,
    val arrangør: String?,
)
