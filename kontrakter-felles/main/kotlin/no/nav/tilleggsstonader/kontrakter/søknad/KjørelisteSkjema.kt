package no.nav.tilleggsstonader.kontrakter.søknad

data class KjørelisteSkjema(
    val reisedagerPerUkeAvsnitt: List<UkeMedReisedager>,
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjema

data class UkeMedReisedager(
    val ukeLabel: String,
    val reisedager: List<Reisedag>,
)

data class Reisedag(
    val dato: DatoFelt,
    val parkeringsutgift: NumeriskFelt? = null,
)
