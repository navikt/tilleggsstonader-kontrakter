package no.nav.tilleggsstonader.kontrakter.søknad.roah

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.DatoFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.felles.AktivitetAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.felles.AnnenAktivitetType
import no.nav.tilleggsstonader.kontrakter.søknad.felles.ReisemåteAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.Adresse

data class ROAHAktivitetAvsnitt(
    override val aktiviteter: EnumFlereValgFelt<String>?,
    override val annenAktivitet: EnumFelt<AnnenAktivitetType>?,
    override val lønnetAktivitet: EnumFelt<JaNei>?,
) : AktivitetAvsnitt

data class ROAHAdresseAvsnitt(
    val måBoBorteHjemmefra: EnumFelt<JaNei>?,
    val fomFlyttedato: DatoFelt?,
    val tomFlyttedato: DatoFelt?,
    val skalReiseFraFolkeregistrertAdresse: EnumFelt<JaNei>?,
    val adresseOriginaltBosted: Adresse?,
    val adresseMidlertidigBosted: Adresse?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Adresser",
        )
}

data class ROAHBarnOgSærligeBehovAvsnitt(
    val harBarnUnder18SomHarFlyttetMed: EnumFelt<JaNei>?,
    val hvilkeBarnFlytterMed: EnumFlereValgFelt<String>?,
    val harBarnHjemmeUnder4Klasse: EnumFelt<JaNei>?,
    val harSærligeBehovForFlereHjemreiser: EnumFelt<JaNei>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Barn og særlige behov",
        )
}

data class ROAHReiseInfo(
    val typeReise: EnumFelt<ROAHTypeReise>?,
    val fom: DatoFelt?,
    val tom: DatoFelt? = null,
    val reisemåte: ReisemåteAvsnitt?,
)

enum class ROAHTypeReise {
    OPPSTART,
    AVSLUTNING,
    HJEMREISE,
}
