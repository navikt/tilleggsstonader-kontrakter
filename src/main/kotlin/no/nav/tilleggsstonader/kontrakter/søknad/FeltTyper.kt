package no.nav.tilleggsstonader.kontrakter.søknad

import java.time.LocalDate
import java.time.YearMonth

data class BooleanFelt(
    val label: String,
    val verdi: Boolean,
)

data class TekstFelt(
    val label: String,
    val verdi: String,
    val svarTekst: String? = null,
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

data class ListFelt<T>(
    val label: String,
    val verdi: List<T>,
    val alternativer: List<String>? = null,
    val svarid: List<T>? = null,
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
