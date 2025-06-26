@file:Suppress("ktlint:standard:enum-entry-name-case")

package no.nav.tilleggsstonader.kontrakter.søknad.dagligReise.fyllutsendinn

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

/**
 * Feltene i [SkjemaDagligReise] kan genereres automatisk fra [FyllUtSendInnSkjemaParser] i test
 * Felter som ikke er definierte vil feile, for å unngå at man ikke plukker opp nye felter hvis det blir lagt till i søknadsdialogen.
 */
@JsonIgnoreProperties("metadata", "state", "_vnote", ignoreUnknown = false)
data class DagligReiseFyllUtSendInnData(
    val data: SkjemaDagligReise,
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
    val adresseOgReisemate: List<AdresseOgReisemate>,
)

data class AdresseOgReisemate(
    val gateadresse: String,
    val postnr: String,
    val poststed: String,
    val hvorMangeDagerIUkenSkalDuMoteOppPaAktivitetstedet1: Valgfelt,
    val harDu6KmReisevei: JaNeiType,
    val hvorLangErReiseveienDin: Int?,
    val harDuAvMedisinskeArsakerBehovForTransportUavhengigAvReisensLengde: JaNeiType?,
    val kanDuReiseMedOffentligTransport: JaNeiType,
    val hvaSlagsTypeBillettMaDuKjope: Map<HvaSlagsTypeBillettMaDuKjopeType, Boolean>?,
    val manedskort3: Int?,
    val manedskort2: Int?,
    val manedskort1: Int?,
    val hvaErViktigsteGrunnerTilAtDuIkkeKanBrukeOffentligTransport:
        Map<HvaErViktigsteGrunnerTilAtDuIkkeKanBrukeOffentligTransportType, Boolean>?,
    val kanDuKjoreMedEgenBil: JaNeiType?,
    val utgifterTilKjoringMedBil: UtgifterTilKjoringMedBil?,
    val hvaErViktigsteGrunnerTilAtDuIkkeKanBrukeOffentligTransport1:
        Map<HvaErViktigsteGrunnerTilAtDuIkkeKanBrukeOffentligTransport1Type, Boolean>?,
)

data class UtgifterTilKjoringMedBil(
    val bompengerEnVei1: Int?,
    val bompengerEnVei: Int?,
    val bompengerEnVei2: Int?,
    val bompengerEnVei3: Int?,
)

data class Valgfelt(
    val label: String,
    val value: String,
)

data class Aktiviteter(
    val aktiviteterOgMaalgruppe: AktiviteterOgMålgruppe,
    val arbeidsrettetAktivitet: ArbeidsrettetAktivitetType?,
    val reiseperiode2: Reiseperiode2?,
    val mottarLonnGjennomTiltak: JaNeiType?,
    val skalDuMoteOppPaAktivitetsstedetIHelePeriodenAktivitetenVarer: JaNeiType?,
    val reiseperiode1: Reiseperiode1?,
)

data class Reiseperiode1(
    val fraOgMedDdMmAaaa1: LocalDate,
    val fraOgMedDdMmAaaa2: LocalDate,
)

data class Reiseperiode2(
    val fraOgMedDdMmAaaa1: LocalDate,
    val fraOgMedDdMmAaaa2: LocalDate,
)

data class AktiviteterOgMålgruppe(
    val aktivitet: Aktivitet,
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
    val adresse: NavAdresse?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class NavAdresse(
    val gyldigFraOgMed: LocalDate,
    val adresse: String,
    val postnummer: String,
    val bySted: String,
    val landkode: String,
    val land: Landvelger,
)

data class Identitet(
    val identitetsnummer: String,
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
    harIngenArbeidsrettetAktivitet,
}

enum class HvaSlagsTypeBillettMaDuKjopeType {
    enkeltbillett,
    ukeskort,
    manedskort,
}

enum class HvaErViktigsteGrunnerTilAtDuIkkeKanBrukeOffentligTransportType {
    helsemessigeArsaker,
    darligTransporttilbud,
    leveringHentingIBarnehageEllerSkolefritidsordningSfoAks,
    annet,
}

enum class HvaErViktigsteGrunnerTilAtDuIkkeKanBrukeOffentligTransport1Type {
    helsemessigeArsaker,
    darligTransporttilbud,
    annet,
}
