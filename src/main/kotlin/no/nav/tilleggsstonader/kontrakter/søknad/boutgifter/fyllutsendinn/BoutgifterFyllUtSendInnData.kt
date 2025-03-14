package no.nav.tilleggsstonader.kontrakter.søknad.boutgifter.fyllutsendinn

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties("metadata", "state", "_vnote", ignoreUnknown = false)
data class BoutgifterFyllUtSendInnData(
    val data: InnerData,
)

/**
 * [dineOpplysninger] er informasjon om person, navn, adress ets som ikke er så interessant å parsa
 */
@JsonIgnoreProperties("dineOpplysninger", "jegBekrefterAtJegVilSvareSaRiktigSomJegKan1", ignoreUnknown = false)
data class InnerData(
    val aktiviteterOgMaalgruppe: AktiviteterOgMaalgruppe,
    val mottarDuEllerHarDuNyligSoktOmNoeAvDette: MottarDuEllerHarDuNyligSoktOmNoeAvDette,
    val jobberDuIEtAnnetLandEnnNorge: String?,
    val hvilketLandJobberDuI: String?,
    val mottarDuPengestotteFraEtAnnetLandEnnNorge1: String?,
    val hvilkenArbeidsrettetAktivitetHarDu: HvilkenArbeidsrettetAktivitetHarDu,
    val hvilkeUtgifterSokerDuOmAFaStotteTil: String?,
    val harDuUtgifterTilBoligBadePaHjemstedetOgAktivitetssted: String?,
    val harDuHoyereUtgifterTilBoligPaDittNyttBosted: String?,
    val mottarBostotte: String?,
    val harDuSaerligStoreUtgifterBoligPaGrunnAvEnFunksjonsnedsettelse: String?,
)

data class AktiviteterOgMaalgruppe(
    val aktivitet: Aktivitet,
)

data class Aktivitet(
    val aktivitetId: String,
    val text: String,
)

data class MottarDuEllerHarDuNyligSoktOmNoeAvDette(
    val arbeidsavklaringspengerAap: Boolean,
    val overgangsstonadTilEnsligMorEllerFar: Boolean,
    val gjenlevendepensjonEtterlattepensjonOmstillingsstonad: Boolean,
    @JsonProperty("uforetrygd")
    val uføretrygd: Boolean,
    val tiltakspenger: Boolean,
    val dagpenger: Boolean,
    val sykepenger: Boolean,
    val kvalifiseringsstonad: Boolean,
    val mottarIngenPengestotteFraNavMenMittLokaleNavKontorHarVurdertAtJegHarNedsattArbeidsevneSomGjorDetVanskeligereForMegAJobbe: Boolean,
    val ingenAvAlternativenePasserForMeg: Boolean,
)

data class HvilkenArbeidsrettetAktivitetHarDu(
    val tiltakArbeidsrettetUtredning: Boolean,
    val utdanningGodkjentAvNav: Boolean,
    val harIngenArbeidsrettetAktivitet: Boolean,
)
