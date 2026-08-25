package no.nav.tilleggsstonader.kontrakter.søknad.læremidler

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei

data class UtdanningAvsnitt(
    val aktiviteter: EnumFlereValgFelt<String>? = null,
    val annenUtdanning: EnumFelt<AnnenUtdanningType>? = null,
    val harRettTilUtstyrsstipend: HarRettTilUtstyrsstipend? = null,
    val harFunksjonsnedsettelse: EnumFelt<JaNei>,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Utdanning",
        )
}

data class HarRettTilUtstyrsstipend(
    val erLærlingEllerLiknende: EnumFelt<JaNei>? = null,
    val harTidligereFullførtVgs: EnumFelt<JaNei>? = null,
    val tarOpplæringVgsSamtidig: EnumFelt<JaNei>? = null,
)

enum class AnnenUtdanningType {
    @Deprecated("Skal deles opp i videoregående og forkurs")
    VIDEREGÅENDE_FORKURS,
    VIDEREGÅENDE,
    FORKURS,
    FAGSKOLE_HØGSKOLE_UNIVERSITET,
    KURS_LIKNENDE,
    INGEN_UTDANNING,
}
