package no.nav.tilleggsstonader.kontrakter.ytelse

import java.time.LocalDate

data class PerioderEnsligForsørger(
    val suksess: Boolean,
    val perioder: List<PeriodeEnsligForsørger>,
)

data class PeriodeEnsligForsørger(
    val fom: LocalDate,
    val tom: LocalDate,
)
