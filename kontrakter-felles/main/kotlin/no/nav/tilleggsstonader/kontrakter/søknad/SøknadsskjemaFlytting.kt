package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.flytting.FlyttingDetaljer

data class SøknadsskjemaFlytting(
    val flyttingDetaljer: FlyttingDetaljer?,
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjemadata {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Søknad om støtte til flytting",
        )
}
