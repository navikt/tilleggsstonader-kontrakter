package no.nav.tilleggsstonader.kontrakter.ytelse

import java.time.LocalDate

data class PerioderArbeidsavklaringspenger(
    val suksess: Boolean,
    val perioder: List<PeriodeArbeidsavklaringspenger>,
)

data class PeriodeArbeidsavklaringspenger(
    val fom: LocalDate,
    val tom: LocalDate,
)
