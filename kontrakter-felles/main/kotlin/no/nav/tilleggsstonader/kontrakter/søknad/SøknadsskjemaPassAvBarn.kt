package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.felles.HovedytelseAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.passavbarn.BarnAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.passavbarn.PassAvBarnAktivitetAvsnitt

data class SøknadsskjemaPassAvBarn(
    val hovedytelse: HovedytelseAvsnitt,
    val aktivitet: PassAvBarnAktivitetAvsnitt,
    val barn: BarnAvsnitt,
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjemadata {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Søknad om støtte til pass av barn",
        )
}
