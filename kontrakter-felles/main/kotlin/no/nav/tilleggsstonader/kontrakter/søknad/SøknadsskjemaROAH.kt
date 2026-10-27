package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.felles.HovedytelseAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.roah.ROAHAdresseAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.roah.ROAHAktivitetAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.roah.ROAHBarnOgSærligeBehovAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.roah.ROAHReiseInfo

data class SøknadsskjemaROAH(
    val hovedytelse: HovedytelseAvsnitt,
    val aktivitet: ROAHAktivitetAvsnitt,
    val adresser: ROAHAdresseAvsnitt,
    val barnOgSærligeBehov: ROAHBarnOgSærligeBehovAvsnitt,
    val reiser: List<ROAHReiseInfo>,
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjemadata {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Søknad om støtte til reise ved oppstart, avslutning og hjemreise",
        )
}
