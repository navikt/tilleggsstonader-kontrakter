package no.nav.tilleggsstonader.kontrakter.søknad.barnetilsyn

import no.nav.tilleggsstonader.kontrakter.felles.Hovedytelse
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.TekstFelt

// TODO slett når sak og søknad er merget
@Deprecated("Flyttet", ReplaceWith("no.nav.tilleggsstonader.kontrakter.søknad.SøknadsskjemaBarnetilsyn"))
typealias SøknadsskjemaBarnetilsyn = no.nav.tilleggsstonader.kontrakter.søknad.SøknadsskjemaBarnetilsyn

data class HovedytelseAvsnitt(
    val hovedytelse: EnumFlereValgFelt<Hovedytelse>,
    val boddSammenhengende: EnumFelt<JaNei>?,
    val planleggerBoINorgeNeste12mnd: EnumFelt<JaNei>?,
)

data class AktivitetAvsnitt(
    val utdanning: EnumFelt<JaNei>,
)

data class BarnAvsnitt(
    val barnMedBarnepass: List<BarnMedBarnepass>,
)

data class BarnMedBarnepass(
    val navn: TekstFelt,
    val ident: TekstFelt,
    val type: EnumFelt<TypeBarnepass>,
    val startetIFemte: EnumFelt<JaNei>?,
    val årsak: EnumFelt<ÅrsakBarnepass>?,
)

enum class TypeBarnepass {
    BARNEHAGE_SFO_AKS,
    ANDRE,
}

enum class ÅrsakBarnepass {
    TRENGER_MER_PASS_ENN_JEVNALDRENDE,
    MYE_BORTE_ELLER_UVANLIG_ARBEIDSTID,
    INGEN_AV_DISSE,
}
