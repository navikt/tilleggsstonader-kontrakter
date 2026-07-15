package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.felles.HovedytelseAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.læremidler.UtdanningAvsnitt

data class SøknadsskjemaLæremidler(
    val hovedytelse: HovedytelseAvsnitt,
    val utdanning: UtdanningAvsnitt,
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjemadata {
    override fun getSpråkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Søknad om støtte læremidler",
        )
}
