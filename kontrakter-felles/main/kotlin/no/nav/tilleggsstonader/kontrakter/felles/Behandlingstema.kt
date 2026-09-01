package no.nav.tilleggsstonader.kontrakter.felles

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Behandlingstema(
    @JsonValue val value: String,
) {
    PassAvBarn("ab0300"), // Pass av barn tilleggsstønad
    Læremidler("ab0292"), // Læremidler tilleggsstønad
    Boutgifter("ab0286"), // Boutgifter tilleggsstønad
    DagligReiseTSO("ab0288"), // Daglig reise TSO
    DagligReiseTSR("ab0287"), // Daglig reise TSR
    ReiseTilSamlingTSO("ab0294"), // Reise til samling TSO
    ReiseTilSamlingTSR("ab0293"), // Reise til samling TSR
    FlyttingTSO("ab0290"), // Flytting TSO
    FlyttingTSR("ab0289"), // Flytting TSR

    ReiseOppstartAvslutningHjemreiseTSO("ab0296"), // Støtte til reise oppstart/avslutning/hjemreise TSO
    ReiseOppstartAvslutningHjemreiseTSR("ab0295"), // Støtte til reise oppstart/avslutning/hjemreise TSR
    Feilutbetaling("ab0006"),
    Tilbakebetaling("ab0007"),
    ;

    companion object {
        private val behandlingstemaMap = entries.associateBy(Behandlingstema::value) + entries.associateBy { it.name }

        @JvmStatic
        @JsonCreator
        fun fromValue(value: String): Behandlingstema = behandlingstemaMap[value] ?: error("Fant ikke Behandlingstema for value=$value")
    }
}

fun Stønadstype.tilBehandlingstema(): Behandlingstema =
    when (this) {
        Stønadstype.BARNETILSYN -> Behandlingstema.PassAvBarn
        Stønadstype.LÆREMIDLER -> Behandlingstema.Læremidler
        Stønadstype.BOUTGIFTER -> Behandlingstema.Boutgifter
        Stønadstype.DAGLIG_REISE_TSO -> Behandlingstema.DagligReiseTSO
        Stønadstype.DAGLIG_REISE_TSR -> Behandlingstema.DagligReiseTSR
        Stønadstype.REISE_TIL_SAMLING_TSO -> Behandlingstema.ReiseTilSamlingTSO
        Stønadstype.REISE_TIL_SAMLING_TSR -> Behandlingstema.ReiseTilSamlingTSR
        Stønadstype.FLYTTING_TSO -> Behandlingstema.FlyttingTSO
        Stønadstype.FLYTTING_TSR -> Behandlingstema.FlyttingTSR
        Stønadstype.REISE_OPPSTART_AVSLUTNING_HJEMREISE_TSO -> Behandlingstema.ReiseOppstartAvslutningHjemreiseTSO
        Stønadstype.REISE_OPPSTART_AVSLUTNING_HJEMREISE_TSR -> Behandlingstema.ReiseOppstartAvslutningHjemreiseTSR
    }
