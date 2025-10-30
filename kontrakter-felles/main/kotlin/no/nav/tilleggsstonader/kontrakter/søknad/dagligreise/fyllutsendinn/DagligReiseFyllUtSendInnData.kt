@file:Suppress("ktlint:standard:enum-entry-name-case")

package no.nav.tilleggsstonader.kontrakter.søknad.dagligreise.fyllutsendinn

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

/**
 * Feltene i [SkjemaDagligReise] kan genereres automatisk fra [FyllUtSendInnSkjemaParser] i test
 * Felter som ikke er definierte vil feile, for å unngå at man ikke plukker opp nye felter hvis det blir lagt till i søknadsdialogen.
 */
@JsonIgnoreProperties("state", "_vnote", ignoreUnknown = false)
data class DagligReiseFyllUtSendInnData(
    val data: SkjemaDagligReise,
    val metadata: MetadataDagligReise,
)

/**
 * [jegBekrefterAtJegVilSvareSaRiktigSomJegKan] er ikke så interessant å mappe
 */
@JsonIgnoreProperties("jegBekrefterAtJegVilSvareSaRiktigSomJegKan", ignoreUnknown = false)
data class SkjemaDagligReise(
    val dineOpplysninger: DineOpplysninger,
    val hovedytelse: Map<HovedytelseType, Boolean>,
    val arbeidOgOpphold: ArbeidOgOpphold?,
    val aktiviteter: Aktiviteter,
    val reise: List<Reise>,
)

data class Reise(
    val gateadresse: String,
    val postnr: String,
    val poststed: String,
    val fom: LocalDate,
    val tom: LocalDate,
    val hvorMangeDagerIUkenSkalDuMoteOppPaAktivitetstedet: Valgfelt,
    val harDu6KmReisevei: JaNeiType,
    val harDuAvMedisinskeArsakerBehovForTransportUavhengigAvReisensLengde: JaNeiType?,
    val hvorLangErReiseveienDin: Int,
    val kanDuReiseMedOffentligTransport: KanDuReiseMedOffentligTransportType,
    val hvaErViktigsteGrunnerTilAtDuIkkeKanBrukeOffentligTransport:
        Map<HvaErViktigsteGrunnerTilAtDuIkkeKanBrukeOffentligTransportType, Boolean>?,
    val kanKjoreMedEgenBil: JaNeiType?,
    val mottarDuGrunnstonadFraNav: JaNeiType?,
    val hvorforIkkeBil: Map<HvorforIkkeBilType, Boolean>?,
    val reiseMedTaxi: JaNeiType?,
    val ttKort: JaNeiType?,
    val hvaSlagsTypeBillettMaDuKjope: Map<HvaSlagsTypeBillettMaDuKjopeType, Boolean>?,
    val enkeltbillett: Int?,
    val syvdagersbillett: Int?,
    val manedskort: Int?,
    val hvorSkalDuKjoreMedEgenBil: Map<HvorSkalDuKjoreMedEgenBilType, Boolean>?,
    val hvorLangErReiseveienDinMedBil: Int?,
    val parkering: JaNeiType?,
    val bompenger: Int?,
    val ferge: Int?,
    val piggdekkavgift: Int?,
)

data class Valgfelt(
    val label: String,
    val value: String,
)

data class Aktiviteter(
    val aktiviteterOgMaalgruppe: Map<String, Boolean>?,
    val arbeidsrettetAktivitet: ArbeidsrettetAktivitetType?,
    val faktiskeUtgifter: DekkesUtgiftenAvAndre,
)

data class DekkesUtgiftenAvAndre(
    val garDuPaVideregaendeEllerGrunnskole: TypeUtdanning,
    val erDuLaerling: JaNeiType?,
    val arbeidsgiverDekkerUtgift: JaNeiType?,
    val bekreftelsemottarIkkeSkoleskyss: Boolean?,
    val lonnGjennomTiltak: JaNeiType?,
)

data class Aktivitet(
    val aktivitetId: String,
    val text: String,
    val periode: PeriodeAktivitet?,
    val maalgruppe: Målgruppe?,
)

data class Målgruppe(
    val maalgruppetype: String,
    val gyldighetsperiode: Periode,
    val maalgruppenavn: String,
)

data class Periode(
    val fom: LocalDate,
    val tom: LocalDate,
)

data class PeriodeAktivitet(
    val fom: LocalDate,
    val tom: LocalDate?,
)

data class ArbeidOgOpphold(
    val jobberIAnnetLand: JaNeiType,
    val jobbAnnetLand: Landvelger?,
    val harPengestotteAnnetLand: Map<HarPengestotteAnnetLandType, Boolean>,
    val pengestotteAnnetLand: Landvelger?,
    val harOppholdUtenforNorgeSiste12mnd: JaNeiType,
    val oppholdUtenforNorgeSiste12mnd: OppholdUtenforNorge?,
    val harOppholdUtenforNorgeNeste12mnd: JaNeiType?,
    val oppholdUtenforNorgeNeste12mnd: OppholdUtenforNorge?,
)

data class OppholdUtenforNorge(
    val land: Landvelger,
    val arsakOppholdUtenforNorge: Map<ArsakOppholdUtenforNorgeType, Boolean>,
    val periode: Periode,
)

data class Landvelger(
    val value: String,
    val label: String,
)

data class DineOpplysninger(
    val fornavn: String,
    val etternavn: String,
    val identitet: Identitet,
    val fodselsdato2: String?,
    val adresse: NavAdresse?,
    val reiseFraFolkeregistrertAdr: JaNeiType,
    val adresseJegSkalReiseFra: AdresseJegSkalReiseFra?,
)

data class AdresseJegSkalReiseFra(
    val gateadresse: String,
    val postnr: String,
    val poststed: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class NavAdresse(
    val gyldigFraOgMed: LocalDate?,
    val adresse: String?,
    val postnummer: String?,
    val bySted: String?,
    val landkode: String?,
    val land: Landvelger?,
)

data class Identitet(
    val identitetsnummer: String,
)

data class MetadataDagligReise(
    val dataFetcher: DataFetcher,
)

data class DataFetcher(
    val aktiviteter: AktiviteterMetadata,
)

data class AktiviteterMetadata(
    val aktiviteterOgMaalgruppe: AktiviteterOgMålgruppeMetadata,
)

data class AktiviteterOgMålgruppeMetadata(
    val data: List<AktivitetMetadata>,
)

data class AktivitetMetadata(
    val value: String,
    val label: String,
    val type: String?,
)

enum class HovedytelseType {
    arbeidsavklaringspenger,
    overgangsstonad,
    gjenlevendepensjon,
    uforetrygd,
    tiltakspenger,
    dagpenger,
    sykepenger,
    kvalifiseringsstonad,
    mottarIngenPengestotte,
    ingenAvAlternativenePasserForMeg,
}

enum class JaNeiType {
    ja,
    nei,
}

enum class HarPengestotteAnnetLandType {
    sykepenger,
    pensjon,
    annenPengestotte,
    mottarIkkePengestotte,
}

enum class ArsakOppholdUtenforNorgeType {
    jobbet,
    studerte,
    fikkMedisinskBehandling,
    varPaFerie,
    besokteFamilie,
    annet,
}

enum class ArbeidsrettetAktivitetType {
    tiltakArbeidsrettetUtredning,
    utdanningGodkjentAvNav,
    jegErArbeidssoker,
    harIngenArbeidsrettetAktivitet,
}

enum class TypeUtdanning {
    videregaendeSkole,
    opplaeringForVoksne,
    annetTiltak,
}

enum class KanDuReiseMedOffentligTransportType {
    ja,
    nei,
    kombinertTogBil,
}

enum class HvaErViktigsteGrunnerTilAtDuIkkeKanBrukeOffentligTransportType {
    helsemessigeArsaker,
    darligTransporttilbud,
    leveringHentingIBarnehageEllerSkolefritidsordningSfoAks,
    annet,
}

enum class HvorforIkkeBilType {
    helsemessigeArsaker,
    harIkkeBilEllerForerkort,
    annet,
}

enum class HvaSlagsTypeBillettMaDuKjopeType {
    enkeltbillett,
    ukeskort,
    manedskort,
}

enum class HvorSkalDuKjoreMedEgenBilType {
    togstasjon,
    busstopp,
    fergeBatkai,
}
