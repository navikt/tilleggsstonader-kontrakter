package no.nav.tilleggsstonader.kontrakter.søknad.felles

import no.nav.tilleggsstonader.kontrakter.felles.Hovedytelse
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt

data class HovedytelseAvsnitt(
    val hovedytelse: EnumFlereValgFelt<Hovedytelse>,
    val arbeidOgOpphold: ArbeidOgOpphold?,
)
