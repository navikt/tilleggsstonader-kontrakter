package no.nav.tilleggsstonader.kontrakter.søknad.felles

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.DatoFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.SelectFelt

data class ArbeidOgOppholdAvsnitt(
    val jobberIAnnetLand: EnumFelt<JaNei>?,
    val jobbAnnetLand: SelectFelt<String>?,
    val harPengestøtteAnnetLand: EnumFlereValgFelt<TypePengestøtte>?,
    val pengestøtteAnnetLand: SelectFelt<String>?,
    val harOppholdUtenforNorgeSiste12mnd: EnumFelt<JaNei>?,
    val oppholdUtenforNorgeSiste12mnd: List<OppholdUtenforNorge>,
    val harOppholdUtenforNorgeNeste12mnd: EnumFelt<JaNei>?,
    val oppholdUtenforNorgeNeste12mnd: List<OppholdUtenforNorge>,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Arbeid og opphold",
        )
}

data class OppholdUtenforNorge(
    val land: SelectFelt<String>,
    val årsak: EnumFlereValgFelt<ÅrsakOppholdUtenforNorge>,
    val fom: DatoFelt,
    val tom: DatoFelt,
)

enum class TypePengestøtte {
    SYKEPENGER,
    PENSJON,
    ANNEN_PENGESTØTTE,
    MOTTAR_IKKE,
}

enum class ÅrsakOppholdUtenforNorge {
    JOBB,
    STUDIER,
    MEDISINSK_BEHANDLING,
    FERIE,
    FAMILIE_BESØK,
    ANNET,
}
