package no.nav.tilleggsstonader.kontrakter.søknad.felles

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.DrosjeInfo
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.OffentligTransportInfo
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.PrivatBilInfo
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.Transportmiddel
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.UnntakFraOffentligTransport
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.ÅrsakKanIkkeBenytteEgenBil

data class ReisemåteAvsnitt(
    val hvilkeTransportmidlerBleBenyttet: EnumFlereValgFelt<Transportmiddel>?,
    val unntakFraOffentligTransport: UnntakFraOffentligTransport?,
    val unntakFraPrivatBil: EnumFlereValgFelt<ÅrsakKanIkkeBenytteEgenBil>?,
    val offentligTransport: OffentligTransportInfo?,
    val privatBil: PrivatBilInfo?,
    val drosje: DrosjeInfo?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Reisemåte",
        )
}
