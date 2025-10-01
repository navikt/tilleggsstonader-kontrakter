package no.nav.tilleggsstonader.kontrakter.søknad

import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

data class TekstFelt(
    val label: String,
    val verdi: String,
)

data class NumeriskFelt(
    val label: String,
    val verdi: Int,
)

data class EnumFelt<T>(
    val label: String,
    val verdi: T,
    val svarTekst: String,
    val alternativer: List<String>,
)

data class SelectFelt<T>(
    val label: String,
    val verdi: T,
    val svarTekst: String,
)

data class EnumFlereValgFelt<T>(
    val label: String,
    val verdier: List<VerdiFelt<T>>,
    val alternativer: List<String>,
)

data class VerdiFelt<T>(
    val verdi: T,
    val label: String,
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
    val type: Vedleggstype,
    val label: String,
    val opplastedeVedlegg: List<Dokument> = emptyList(),
    val barnId: String? = null,
)

data class Dokument(
    val id: UUID,
    val navn: String,
)
