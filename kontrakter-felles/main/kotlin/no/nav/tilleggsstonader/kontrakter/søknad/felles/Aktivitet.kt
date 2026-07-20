package no.nav.tilleggsstonader.kontrakter.søknad.felles

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei

enum class Aktivitet {
    TILTAK,
    UTDANNING,
    ARBEIDSSØKER,
    INGEN_AKTIVITET,
}

interface AktivitetAvsnitt : Avsnitt {
    val aktiviteter: EnumFlereValgFelt<String>?
    val annenAktivitet: EnumFelt<Aktivitet>?
    val lønnetAktivitet: EnumFelt<JaNei>?

    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Aktivitet",
        )
}
