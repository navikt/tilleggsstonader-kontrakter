@file:Suppress("ktlint:standard:enum-entry-name-case")

package no.nav.tilleggsstonader.kontrakter.søknad.boutgifter.fyllutsendinn

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

@JsonIgnoreProperties("metadata", "state", "_vnote", ignoreUnknown = false)
data class BoutgifterFyllUtSendInnData(
    val data: SkjemaBoutgifter,
)

/**
 * [dineOpplysninger] er informasjon om person, navn, adress ets som ikke er så interessant å parsa
 * [jegBekrefterAtJegVilSvareSaRiktigSomJegKan] er ikke så interessant å mappe
 */
@JsonIgnoreProperties("dineOpplysninger", "jegBekrefterAtJegVilSvareSaRiktigSomJegKan", ignoreUnknown = false)
data class SkjemaBoutgifter(
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
    val utgifterTilOvernatting: Int,
)

data class FasteUtgifter(
    val harUtgifterTilBoligToSteder: HarUtgifterTilBoligToStederType,
    val utgifterFlereSteder: UtgifterFlereSteder?,
    val utgifterNyBolig: UtgifterNyBolig?,
)

data class UtgifterNyBolig(
    val delerUtgifteneTilBoligMedAndre: JaNeiType,
    val andelUtgifterBolig: Int?,
    val harHoyereUtgifterPaNyttBosted: JaNeiType,
    val mottarBostotte: JaNeiType?,
)

data class UtgifterFlereSteder(
    val delerDuUtgifterTilBoligMedAndre: Map<DelerDuUtgifterTilBoligMedAndreType, Boolean>,
    val andelUtgifterBoligHjemsted: Int?,
    val andelUtgifterBoligAktivitetssted: Int?,
    val harLeieinntekter: JaNeiType,
    val leieinntekterPerManed: Int?,
)

data class Aktiviteter(
    val aktiviteterOgMaalgruppe: AktiviteterOgMålgruppe,
    val arbeidsrettetAktivitet: ArbeidsrettetAktivitetType?,
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
    val harPengestotteAnnetLand: JaNeiType,
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

enum class HovedytelseType{
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

enum class JaNeiType{
    ja,
    nei,
}

enum class ArsakOppholdUtenforNorgeType{
    jobbet,
    studerte,
    fikkMedisinskBehandling,
    varPaFerie,
    besokteFamilie,
    annet,
}

enum class ArbeidsrettetAktivitetType{
    tiltakArbeidsrettetUtredning,
    utdanningGodkjentAvNav,
    harIngenArbeidsrettetAktivitet,
}

enum class TypeUtgifterType{
    fastUtgift,
    midlertidigUtgift,
}

enum class HarUtgifterTilBoligToStederType{
    ekstraBolig,
    nyBolig,
}

enum class DelerDuUtgifterTilBoligMedAndreType{
    jaPaHjemstedet,
    jaPaAktivitetssted,
    nei,
}


