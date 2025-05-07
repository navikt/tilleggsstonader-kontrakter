@file:Suppress("ktlint:standard:enum-entry-name-case")

package no.nav.tilleggsstonader.kontrakter.søknad.boutgifter.fyllutsendinn

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

@JsonIgnoreProperties("metadata", "state", "_vnote", ignoreUnknown = false)
data class BoutgifterFyllUtSendInnData(
    val data: SkjemaBoutgifter,
)

/**
 * [jegBekrefterAtJegVilSvareSaRiktigSomJegKan] er ikke så interessant å mappe
 */
@JsonIgnoreProperties("jegBekrefterAtJegVilSvareSaRiktigSomJegKan", ignoreUnknown = false)
data class SkjemaBoutgifter(
    val dineOpplysninger: DineOpplysninger,
    val hovedytelse: Map<HovedytelseType, Boolean>,
    val harNedsattArbeidsevne: JaNeiType?,
    val arbeidOgOpphold: ArbeidOgOpphold?,
    val aktiviteter: Aktiviteter,
    val boligEllerOvernatting: BoligEllerOvernatting,
)

data class BoligEllerOvernatting(
    val typeUtgifter: TypeUtgifterType,
    val fasteUtgifter: FasteUtgifter?,
    val samling: Samling?,
    val harSaerligStoreUtgifterPaGrunnAvFunksjonsnedsettelse: JaNeiType,
)

data class Samling(
    val periodeForSamling: List<PeriodeForSamling>,
)

data class PeriodeForSamling(
    val fom: LocalDate,
    val tom: LocalDate,
    val trengteEkstraOvernatting: JaNeiType,
    val utgifterTilOvernatting: Int?,
)

data class FasteUtgifter(
    val harUtgifterTilBoligToSteder: HarUtgifterTilBoligToStederType,
    val utgifterFlereSteder: UtgifterFlereSteder?,
    val utgifterNyBolig: UtgifterNyBolig?,
)

data class UtgifterNyBolig(
    val delerBoutgifter: JaNeiType,
    val andelUtgifterBolig: Int?,
    val harHoyereUtgifterPaNyttBosted: JaNeiType,
    val mottarBostotte: JaNeiType,
)

data class UtgifterFlereSteder(
    val delerBoutgifter: Map<DelerBoutgifterType, Boolean>,
    val andelUtgifterBoligHjemsted: Int,
    val andelUtgifterBoligAktivitetssted: Int,
)

data class Aktiviteter(
    val aktiviteterOgMaalgruppe: AktiviteterOgMålgruppe,
    val arbeidsrettetAktivitet: ArbeidsrettetAktivitetType?,
    val mottarLonnGjennomTiltak: JaNeiType?,
)

data class AktiviteterOgMålgruppe(
    val aktivitet: Aktivitet,
)

data class Aktivitet(
    val aktivitetId: String,
    val text: String,
    val periode: Periode?,
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

enum class TypeUtgifterType {
    fastUtgift,
    midlertidigUtgift,
}

enum class HarUtgifterTilBoligToStederType {
    ekstraBolig,
    nyBolig,
}

enum class DelerBoutgifterType {
    hjemsted,
    aktivitetssted,
    nei,
}
