package no.nav.tilleggsstonader.kontrakter.søknad.barnetilsyn

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.DatoFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.TekstFelt

data class AktivitetAvsnitt(
    val aktiviteter: EnumFlereValgFelt<String>?,
    val annenAktivitet: EnumFelt<AnnenAktivitetType>?,
    val lønnetAktivitet: EnumFelt<JaNei>?,
) : Avsnitt {
    override fun getSpråkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Aktivitet",
        )
}

enum class AnnenAktivitetType {
    TILTAK,
    UTDANNING,
    ARBEIDSSØKER,
    INGEN_AKTIVITET,
}

data class BarnAvsnitt(
    val barnMedBarnepass: List<BarnMedBarnepass>,
) : Avsnitt {
    override fun getSpråkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Barn",
        )
}

data class BarnMedBarnepass(
    val navn: TekstFelt,
    val ident: TekstFelt,
    val type: EnumFelt<TypeBarnepass>,
    val utgifter: Utgifter? = null, // fjern default null når denne er tatt i bruk rundt om
    val startetIFemte: EnumFelt<JaNei>?,
    val årsak: EnumFelt<ÅrsakBarnepass>?,
)

data class Utgifter(
    val harUtgifterTilPassHelePerioden: EnumFelt<JaNei>,
    val fom: DatoFelt?,
    val tom: DatoFelt?,
)

enum class TypeBarnepass {
    BARNEHAGE_SFO_AKS,
    PRIVAT,
}

enum class ÅrsakBarnepass {
    TRENGER_MER_PASS_ENN_JEVNALDRENDE,
    MYE_BORTE_ELLER_UVANLIG_ARBEIDSTID,
    INGEN_AV_DISSE,
}
