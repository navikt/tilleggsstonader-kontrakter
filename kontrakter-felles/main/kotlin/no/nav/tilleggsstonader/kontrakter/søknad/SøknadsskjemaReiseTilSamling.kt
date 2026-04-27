package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.søknad.barnetilsyn.AktivitetAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.felles.HovedytelseAvsnitt

data class SøknadsskjemaReiseTilSamling(
    val hovedytelse: HovedytelseAvsnitt,
    val aktivitet: AktivitetAvsnitt,
    val samlinger: List<PeriodeFelt>?,
    val oppmøteadresse: AdresseFelt?,
    val kanReiseKollektivt: EnumFelt<JaNei>?,
    val totalbeløpKollektivt: HeltallFelt?,
    val årsakIkkeKollektivt: EnumFelt<ÅrsakIkkeKollektivt>?,
    val kanBenytteEgenBil: EnumFelt<JaNei>?,
    val årsakIkkeEgenBil: EnumFelt<ÅrsakIkkeEgenBil>?,
    val kanBenytteDrosje: EnumFelt<JaNei>?,
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjemadata {
    enum class ÅrsakIkkeKollektivt {
        HELSEMESSIGE_ÅRSAKER,
        DÅRLIG_TRANSPORTTILBUD,
        HENTING_LEVERING_BARN,
        ANNET,
    }

    enum class ÅrsakIkkeEgenBil {
        HELSEMESSIGE_ÅRSAKER,
        DISPONERER_IKKE_BIL,
        ANNET,
    }
}
