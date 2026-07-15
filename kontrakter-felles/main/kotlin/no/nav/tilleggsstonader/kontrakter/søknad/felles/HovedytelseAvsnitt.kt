package no.nav.tilleggsstonader.kontrakter.søknad.felles

import no.nav.tilleggsstonader.kontrakter.felles.Hovedytelse
import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt

data class HovedytelseAvsnitt(
    val hovedytelse: EnumFlereValgFelt<Hovedytelse>,
    val arbeidOgOpphold: ArbeidOgOppholdAvsnitt?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Hovedytelse",
        )
}
