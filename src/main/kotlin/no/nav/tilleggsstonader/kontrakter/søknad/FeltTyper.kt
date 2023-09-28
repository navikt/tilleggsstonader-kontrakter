package no.nav.tilleggsstonader.kontrakter.søknad

import java.time.LocalDate
import java.time.YearMonth

data class TekstFelt(
    val label: String,
    val verdi: String,
)

data class EnumFelt<T>(
    val label: String,
    val verdi: T,
    val svarTekst: String,
)

data class DatoFelt(
    val label: String,
    val verdi: LocalDate,
)

data class ÅrMånedFelt(
    val label: String,
    val verdi: YearMonth,
)

data class HeltallFelt(
    val label: String,
    val verdi: Int,
)

data class PeriodeFelt(
    val label: String,
    val fra: DatoFelt,
    val til: DatoFelt,
)

data class DokumentasjonFelt(
    val id: String,
    val label: String,
    val harSendtInn: Boolean,
    val opplastedeVedlegg: List<Dokument> = emptyList(),
)

data class Dokument(val id: String, val navn: String)
