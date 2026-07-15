package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode

data class KjørelisteSkjema(
    val reiseId: String,
    val reisedagerPerUkeAvsnitt: List<UkeMedReisedager>,
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjemadata {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Søknad om støtte til pass av barn",
        )
}

data class UkeMedReisedager(
    val ukeLabel: String,
    val reisedagerLabel: String,
    val spørsmål: String,
    val reisedager: List<Reisedag>,
)

data class Reisedag(
    val dato: DatoFelt,
    val harKjørt: Boolean,
    val parkeringsutgift: VerdiFelt<Number?>,
)
