package no.nav.tilleggsstonader.kontrakter.felles

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Behandlingstema(
    @JsonValue val value: String,
) {
    TilsynBarn("ab0300"), // Tilsyn av barn tilleggsstønad
    Læremidler("ab0292"), // Læremidler tilleggsstønad
    Boutgifter("ab0286"), // Boutgifter tilleggsstønad
    DagligReise("ab0288"), // Daglig reise tilleggsstønad
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
        Stønadstype.BARNETILSYN -> Behandlingstema.TilsynBarn
        Stønadstype.LÆREMIDLER -> Behandlingstema.Læremidler
        Stønadstype.BOUTGIFTER -> Behandlingstema.Boutgifter
        Stønadstype.DAGLIG_REISE_TSO -> Behandlingstema.DagligReise
        Stønadstype.DAGLIG_REISE_TSR -> Behandlingstema.DagligReise
    }
