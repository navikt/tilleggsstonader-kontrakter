package no.nav.tilleggsstonader.kontrakter.søknad.barnetilsyn

import no.nav.tilleggsstonader.kontrakter.felles.Hovedytelse
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.TekstFelt

data class HovedytelseAvsnitt(
    val hovedytelse: EnumFlereValgFelt<Hovedytelse>,
    @Deprecated("Erstatt med arbeidOgOpphold")
    val boddSammenhengende: EnumFelt<JaNei>? = null,
    @Deprecated("Erstatt med arbeidOgOpphold")
    val planleggerBoINorgeNeste12mnd: EnumFelt<JaNei>? = null,
    // TODO fjern default null når deprecates fjernes
    val arbeidOgOpphold: ArbeidOgOpphold? = null,
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
