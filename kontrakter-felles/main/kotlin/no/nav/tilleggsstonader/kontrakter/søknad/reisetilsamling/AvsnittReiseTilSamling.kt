package no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling

import no.nav.tilleggsstonader.kontrakter.søknad.DatoFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.SelectFelt
import no.nav.tilleggsstonader.kontrakter.søknad.VerdiFelt
import no.nav.tilleggsstonader.kontrakter.søknad.barnetilsyn.AnnenAktivitetType

data class AktivitetAvsnitt(
    val aktiviteter: EnumFlereValgFelt<String>?,
    val annenAktivitet: EnumFelt<AnnenAktivitetType>?,
    val lønnetAktivitet: EnumFelt<JaNei>?,
)

data class Samling(
    val fom: DatoFelt?,
    val tom: DatoFelt?,
    val erObligatorisk: EnumFelt<JaNei>?,
)

data class AdresseAvsnitt(
    val land: SelectFelt<String>?,
    val gateadresse: VerdiFelt<String>?,
    val postnummer: VerdiFelt<String>?,
    val poststed: VerdiFelt<String>?,
)

data class ReiseavstandAvsnitt(
    val reiseFraFolkeregistrertAdr: EnumFelt<JaNei>,
    val adresseDetSkalReisesFra: AdresseAvsnitt,
    val antallKilometerEnVei: VerdiFelt<String>?,
    val aktivitetsadresse: AdresseAvsnitt,
)

data class ReisemåteAvsnitt(
    val kanReiseKollektivt: EnumFelt<JaNei>?,
    val totalutgifterKollektivt: VerdiFelt<String>?,
    val kanBenytteEgenBil: EnumFelt<JaNei>?,
    val kanBenytteDrosje: EnumFelt<JaNei>?,
)
