package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.søknad.felles.HovedytelseAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.laeremidler.UtdanningAvsnitt

data class SøknadsskjemaLæremidler(
    val hovedytelse: HovedytelseAvsnitt,
    val utdanning: UtdanningAvsnitt,
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjema
