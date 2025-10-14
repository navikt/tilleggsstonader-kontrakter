package no.nav.tilleggsstonader.kontrakter.søknad

data class KjørelisteSkjema(
    val reisedagerPerUkeAvsnitt: List<UkeMedReisedager>,
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjemadata

data class UkeMedReisedager(
    val ukeLabel: String,
    val spørsmål: String,
    val reisedager: List<Reisedag>,
)

data class Reisedag(
    val dato: DatoFelt,
    val harKjørt: Boolean,
    val parkeringsutgift: VerdiFelt<Number?>,
)
