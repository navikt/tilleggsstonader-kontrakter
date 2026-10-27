package no.nav.tilleggsstonader.kontrakter.søknad.roah

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.DatoFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.SelectFelt
import no.nav.tilleggsstonader.kontrakter.søknad.VerdiFelt
import no.nav.tilleggsstonader.kontrakter.søknad.felles.AktivitetAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.felles.AnnenAktivitetType
import no.nav.tilleggsstonader.kontrakter.søknad.felles.ReisemåteAvsnitt

data class ROAHAktivitetAvsnitt(
    override val aktiviteter: EnumFlereValgFelt<String>?,
    override val annenAktivitet: EnumFelt<AnnenAktivitetType>?,
    override val lønnetAktivitet: EnumFelt<JaNei>?,
) : AktivitetAvsnitt

data class ROAHAdresseAvsnitt(
    val måBoBorteHjemmefra: EnumFelt<JaNei>?,
    val periodeMidlertidigBosted: PeriodeMidlertidigBostedAvsnitt?,
    val adresseOriginaltBosted: AdresseOriginaltBostedAvsnitt?,
    val adresseMidlertidigBosted: AdresseMidlertidigBostedAvsnitt?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Adresser",
        )
}

data class PeriodeMidlertidigBostedAvsnitt(
    val fomFlyttedato: DatoFelt?,
    val tomFlyttedato: DatoFelt?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "I hvilken periode må du midlertidig bo borte hjemmefra?",
        )
}

data class AdresseOriginaltBostedAvsnitt(
    val skalReiseFraFolkeregistrertAdresse: EnumFelt<JaNei>?,
    val adresseOriginaltBosted: AdresseOriginaltBostedDetaljerAvsnitt?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Adressen du flyttet fra",
        )
}

data class AdresseOriginaltBostedDetaljerAvsnitt(
    val land: SelectFelt<String>?,
    val gateadresse: VerdiFelt<String>?,
    val postnummer: VerdiFelt<String>?,
    val poststed: VerdiFelt<String>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> = mapOf(Språkkode.NB to "Oppgi adressen du flyttet fra")
}

data class AdresseMidlertidigBostedAvsnitt(
    val land: SelectFelt<String>?,
    val gateadresse: VerdiFelt<String>?,
    val postnummer: VerdiFelt<String>?,
    val poststed: VerdiFelt<String>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> = mapOf(Språkkode.NB to "Adressen du midlertidig bor på")
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
