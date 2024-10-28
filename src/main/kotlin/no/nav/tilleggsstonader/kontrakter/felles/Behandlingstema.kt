package no.nav.tilleggsstonader.kontrakter.felles

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Behandlingstema(@JsonValue val value: String) {
    TilsynBarn("ab0300"),
    Læremidler("ab0292"),
    Feilutbetaling("ab0006"),
    Tilbakebetaling("ab0007"),
    ;

    companion object {
        private val behandlingstemaMap = values().associateBy(Behandlingstema::value) + values().associateBy { it.name }

        @JvmStatic
        @JsonCreator
        fun fromValue(value: String): Behandlingstema {
            return behandlingstemaMap[value] ?: error("Fant ikke Behandlingstema for value=$value")
        }
    }
}
