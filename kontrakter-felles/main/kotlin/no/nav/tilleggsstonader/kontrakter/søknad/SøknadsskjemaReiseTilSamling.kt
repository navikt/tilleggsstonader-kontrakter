package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.søknad.felles.HovedytelseAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.AktivitetAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.ReiseavstandAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.ReisemåteAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.Samling

data class SøknadsskjemaReiseTilSamling(
    val hovedytelse: HovedytelseAvsnitt,
    val aktivitet: AktivitetAvsnitt,
    val samlinger: List<Samling>,
    val reiseavstand: ReiseavstandAvsnitt,
    val reisemåte: ReisemåteAvsnitt,
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjemadata
