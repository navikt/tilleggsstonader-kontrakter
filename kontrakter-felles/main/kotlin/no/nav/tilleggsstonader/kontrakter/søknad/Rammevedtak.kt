package no.nav.tilleggsstonader.kontrakter.søknad

import java.time.LocalDate

data class RammevedtakDto(
    val reiseId: String,
    val fom: LocalDate,
    val tom: LocalDate,
    val aktivitetsadresse: String,
    val aktivitetsnavn: String,
    val uker: List<RammevedtakUkeDto>,
    val helligdager: List<HelligdagDto>
)

data class RammevedtakUkeDto(
    val fom: LocalDate,
    val tom: LocalDate,
    val ukeNummer: Int,
    val reisedagerPerUke: Int,
    val innsendtDato: LocalDate?,
    val kanSendeInnKjøreliste: Boolean,
)

data class HelligdagDto(
    val dato: LocalDate,
    val navn: String,
)
