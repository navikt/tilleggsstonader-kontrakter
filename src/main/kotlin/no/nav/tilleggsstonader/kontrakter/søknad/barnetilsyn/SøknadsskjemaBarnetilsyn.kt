package no.nav.tilleggsstonader.kontrakter.søknad.barnetilsyn

import no.nav.tilleggsstonader.kontrakter.felles.Hovedytelse
import no.nav.tilleggsstonader.kontrakter.søknad.BooleanFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei

data class SøknadsskjemaBarnetilsyn(
    val hovedytelse: EnumFelt<Hovedytelse>,
    val aktivitet: Aktivitet,
    val barn: List<BarnMedBarnepass>,
)

data class Aktivitet(
    val utdanning: EnumFelt<JaNei>,
)

data class BarnMedBarnepass(
    val ident: String,
    val type: EnumFelt<TypeBarnepass>,
    val startetIFemte: BooleanFelt?,
    val årsak: EnumFelt<ÅrsakBarnepass>?,
)

enum class TypeBarnepass {
    BARNEHAGE_SFO_AKS,
    ANDRE,
}

enum class ÅrsakBarnepass {
    TRENGER_MER_PASS_ENN_JEVNALDRENDE,
    MYE_BORTE_ELLER_UVANLIG_ARBEIDSTID,
}
