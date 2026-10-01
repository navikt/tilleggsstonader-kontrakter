package no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling

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

data class TilleggsopplysningerAnnenAktivitetAvsnitt(
    val erLærlingEllerLiknende: EnumFelt<JaNei>?,
    val fårDekketReise: EnumFelt<JaNei>?,
    val erUnder25År: EnumFelt<JaNei>?,
    val måBetaleForReiseTilSkole: EnumFelt<JaNei>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Tilleggssopplysninger om annen aktivitet",
        )
}

enum class AktivitetTypeUtdanning {
    VIDEREGÅENDE,
    OPPLÆRING_FOR_VOKSNE,
    ANNET_TILTAK,
}

data class ReiseTilSamlingAktivitetAvsnitt(
    override val aktiviteter: EnumFlereValgFelt<String>?,
    override val annenAktivitet: EnumFelt<AnnenAktivitetType>?,
    override val lønnetAktivitet: EnumFelt<JaNei>?,
    val tilleggsopplysningerAnnenAktivitet: TilleggsopplysningerAnnenAktivitetAvsnitt?,
    val annenAktivitetTypeUtdanning: EnumFelt<AktivitetTypeUtdanning>?,
) : AktivitetAvsnitt

data class Samling(
    val fom: DatoFelt?,
    val tom: DatoFelt?,
    val erObligatorisk: EnumFelt<JaNei>?,
    val adresse: Adresse?,
    val antallKilometerEnVei: VerdiFelt<String>?,
    val reisemåte: ReisemåteAvsnitt? = null,
)

data class Adresse(
    val land: SelectFelt<String>?,
    val gateadresse: VerdiFelt<String>?,
    val postnummer: VerdiFelt<String>?,
    val poststed: VerdiFelt<String>?,
)

data class AvreiseadresseAvsnitt(
    val skalReiseFraFolkeregistrertAdresse: EnumFelt<JaNei>,
    val adresseDetSkalReisesFra: Adresse?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Avreiseadresse",
        )
}

enum class Transportmiddel {
    OFFENTLIG_TRANSPORT,
    PRIVAT_BIL,
    DROSJE,
}

enum class ÅrsakKanIkkeBenytteOffentligTransport {
    DÅRLIG_TRANSPORTTILBUD,
    HELSEMESSIGE_ÅRSAKER,
    LEVERING_HENTING_I_BARNEHAGE,
    FRAKT_AV_NØDVENDIG_UTSTYR,
}

enum class ÅrsakKanIkkeBenytteEgenBil {
    HAR_IKKE_BIL_ELLER_FØRERKORT,
    HELSEMESSIGE_ÅRSAKER,
    FRAKT_AV_NØDVENDIG_UTSTYR,
    ANNET,
}

data class ReisemåteAvsnitt(
    val hvilkeTransportmidlerBleBenyttet: EnumFlereValgFelt<Transportmiddel>?,
    val unntakFraOffentligTransport: UnntakFraOffentligTransport?,
    val unntakFraPrivatBil: EnumFlereValgFelt<ÅrsakKanIkkeBenytteEgenBil>?,
    val offentligTransport: OffentligTransportInfo?,
    val privatBil: PrivatBilInfo?,
    val drosje: DrosjeInfo?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Reisemåte",
        )
}

data class OffentligTransportInfo(
    val totalUtgifterOffentligTransport: VerdiFelt<String>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Offentlig transport",
        )
}

data class PrivatBilInfo(
    val benyttetEgenBil: EnumFelt<JaNei>?,
    val betalteForReisen: EnumFelt<JaNei>?,
    val infoBilKunDelerAvStrekning: InfoBilKunDelerAvStrekning?,
    val utgifterPrivatBil: UtgifterPrivatBil?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Privat bil",
        )
}

data class DrosjeInfo(
    val harTTKort: EnumFelt<JaNei>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Drosje",
        )
}

data class UnntakFraOffentligTransport(
    val årsaker: EnumFlereValgFelt<ÅrsakKanIkkeBenytteOffentligTransport>?,
    val leveringOgHentingIBarnehage: LeveringOgHentingIBarnehage?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Unntak fra offentlig transport",
        )
}

data class LeveringOgHentingIBarnehage(
    val gateadresse: VerdiFelt<String>?,
    val postnummer: VerdiFelt<String>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Levering og henting i barnehage",
        )
}

enum class DrivstoffType {
    BENSIN,
    DIESEL,
    ELBIL,
    HYBRID,
    HYDROGEN,
}

data class UtgifterPrivatBil(
    val bompenger: VerdiFelt<String>?,
    val ferge: VerdiFelt<String>?,
    val piggdekkavgift: VerdiFelt<String>?,
    val parkering: VerdiFelt<String>?,
    val drivstoffType: EnumFelt<DrivstoffType>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Utgifter privat bil",
        )
}

data class InfoBilKunDelerAvStrekning(
    val strekningHvorBilBleBenyttet: VerdiFelt<String>?,
    val antallKilometerKjørt: VerdiFelt<String>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Bil kun deler av strekning",
        )
}
