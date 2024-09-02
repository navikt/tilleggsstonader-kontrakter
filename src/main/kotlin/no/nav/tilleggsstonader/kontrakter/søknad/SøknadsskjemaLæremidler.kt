package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.søknad.barnetilsyn.AktivitetAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.barnetilsyn.BarnAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.barnetilsyn.HovedytelseAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.laeremidler.UtdanningAvsnitt

data class SøknadsskjemaLæremidler(
    val hovedytelse: HovedytelseAvsnitt,
    val utdanning: UtdanningAvsnitt,
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjema
