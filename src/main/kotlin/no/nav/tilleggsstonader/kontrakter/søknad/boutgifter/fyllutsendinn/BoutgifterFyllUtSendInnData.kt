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
    val hovedytelse: Hovedytelse,
    val harNedsattArbeidsevne: String?,
    val arbeidOgOpphold: ArbeidOgOpphold?,
    val aktiviteter: Aktiviteter,
    val boligEllerOvernatting: BoligEllerOvernatting,
)

data class BoligEllerOvernatting(
    val typeUtgifter: String,
    val fasteUtgifter: FasteUtgifter?,
    val samling: Samling?,
    val harSaerligStoreUtgifterPaGrunnAvFunksjonsnedsettelse: String,
)

data class Samling(
    val periodeForSamling: List<PeriodeForSamling>,
)

data class PeriodeForSamling(
    val fom: LocalDate,
    val tom: LocalDate,
    val trengteEkstraOvernatting: String,
    val utgifterTilOvernatting: Int,
)

data class FasteUtgifter(
    val harUtgifterTilBoligToSteder: String,
    val harLeieinntekterSomDekkerUtgifteneTilBoligenPaHjemstedet: String?,
    val harHoyereUtgifterPaNyttBosted: String?,
    val mottarBostotte: String?,
)

data class Aktiviteter(
    val aktiviteterOgMaalgruppe: AktiviteterOgMålgruppe,
    val arbeidsrettetAktivitet: String?,
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
    val jobberIAnnetLand: String,
    val jobbAnnetLand: Landvelger?,
    val harPengestotteAnnetLand: String,
    val pengestotteAnnetLand: Landvelger?,
    val harOppholdUtenforNorgeSiste12mnd: String,
    val oppholdUtenforNorgeSiste12mnd: OppholdUtenforNorge?,
    val harOppholdUtenforNorgeNeste12mnd: String?,
    val oppholdUtenforNorgeNeste12mnd: OppholdUtenforNorge?,
)

data class OppholdUtenforNorge(
    val land: Landvelger,
    val arsakOppholdUtenforNorge: ArsakOppholdUtenforNorge,
    val periode: Periode,
)

data class ArsakOppholdUtenforNorge(
    val jobbet: Boolean,
    val studerte: Boolean,
    val fikkMedisinskBehandling: Boolean,
    val varPaFerie: Boolean,
    val besokteFamilie: Boolean,
    val annet: Boolean,
)

data class Landvelger(
    val value: String,
    val label: String,
)

data class Hovedytelse(
    val arbeidsavklaringspenger: Boolean,
    val overgangsstonad: Boolean,
    val gjenlevendepensjon: Boolean,
    val uforetrygd: Boolean,
    val tiltakspenger: Boolean,
    val dagpenger: Boolean,
    val sykepenger: Boolean,
    val kvalifiseringsstonad: Boolean,
    val mottarIngenPengestotte: Boolean,
    val ingenAvAlternativenePasserForMeg: Boolean,
)
