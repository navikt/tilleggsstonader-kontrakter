package no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.DatoFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFlereValgFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.SelectFelt
import no.nav.tilleggsstonader.kontrakter.søknad.VerdiFelt
import no.nav.tilleggsstonader.kontrakter.søknad.felles.Aktivitet
import no.nav.tilleggsstonader.kontrakter.søknad.felles.AktivitetAvsnitt

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
    override val annenAktivitet: EnumFelt<Aktivitet>?,
    override val lønnetAktivitet: EnumFelt<JaNei>?,
    val tilleggsopplysningerAnnenAktivitet: TilleggsopplysningerAnnenAktivitetAvsnitt?,
    val annenAktivitetTypeUtdanning: EnumFelt<AktivitetTypeUtdanning>?,
) : AktivitetAvsnitt

data class Samling(
    val fom: DatoFelt?,
    val tom: DatoFelt?,
    val erObligatorisk: EnumFelt<JaNei>?,
    val harBruktEkstraReiseDager: EnumFelt<JaNei>?,
    val adresse: Adresse?,
    val antallKilometerEnVei: VerdiFelt<String>?,
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

enum class KanIkkeReiseMedOffentligTransportBegrunnelser {
    DÅRLIG_TRANSPORTTILBUD,
    HELSEMESSIGE_ÅRSAKER,
    LEVERING_HENTING_I_BARNEHAGE,
}

enum class KanBenytteEgenBil {
    JA,
    NEI,
    NEI_SITTER_PÅ_MED_ANDRE,
}

enum class KanIkkeBenytteEgenBilBegrunnelser {
    HAR_IKKE_BIL_ELLER_FØRERKORT,
    HELSEMESSIGE_ÅRSAKER,
    ANNET,
}

data class ReisemåteAvsnitt(
    val kanReiseMedOffentligTransport: EnumFelt<JaNei>,
    val totalUtgifterOffentligTransport: VerdiFelt<String>?,
    val kanIkkeReiseMedOffentligTransportBegrunnelser: EnumFlereValgFelt<KanIkkeReiseMedOffentligTransportBegrunnelser>?,
    val barnehageGateadresse: VerdiFelt<String>?,
    val barnehagePostnummer: VerdiFelt<String>?,
    val kanBenytteEgenBil: EnumFelt<KanBenytteEgenBil>?,
    val kanIkkeBenytteEgenBilBegrunnelser: EnumFlereValgFelt<KanIkkeBenytteEgenBilBegrunnelser>?,
    val ønskerDekketUtgifterForDrosje: EnumFelt<JaNei>?,
    val betalerForReiseSelv: EnumFelt<JaNei>?,
    val harTTKort: EnumFelt<JaNei>?,
    val reiseMedBilUtgifter: ReiseMedBilUtgifterAvsnitt?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Reisemåte",
        )
}

enum class DrivstoffType {
    BENSIN,
    DIESEL,
    ELBIL,
    HYBRID,
    HYDROGEN,
}

data class ReiseMedBilUtgifterAvsnitt(
    val drivstoffType: EnumFelt<DrivstoffType>,
    val bompenger: VerdiFelt<String>?,
    val ferge: VerdiFelt<String>?,
    val piggdekkavgift: VerdiFelt<String>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Utgifter for reise med bil",
        )
}
