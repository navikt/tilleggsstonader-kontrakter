package no.nav.tilleggsstonader.kontrakter.søknad.barnetilsyn

import no.nav.tilleggsstonader.kontrakter.felles.Hovedytelse
import no.nav.tilleggsstonader.kontrakter.søknad.DokumentasjonFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.TekstFelt

data class SøknadsskjemaBarnetilsyn(
    val hovedytelse: HovedytelseAvsnitt,
    val aktivitet: AktivitetAvsnitt,
    val barn: BarnAvsnitt,
    val dokumentasjon: List<DokumentasjonFelt>,
)

data class HovedytelseAvsnitt(
    val hovedytelse: EnumFelt<Hovedytelse>,
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
}
