package no.nav.tilleggsstonader.kontrakter.felles

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Behandlingstema(@JsonValue val value: String) {
    Barnetilsyn("ab0028"), // TODO denne tihører ENF
    Feilutbetaling("ab0006"),
    Tilbakebetaling("ab0007"),
    Klage("ae0058"),
    ;

    companion object {
        private val behandlingstemaMap = values().associateBy(Behandlingstema::value) + values().associateBy { it.name }

        @JvmStatic
        @JsonCreator
        fun fromValue(value: String): Behandlingstema {
            return behandlingstemaMap[value] ?: throw error("Fant ikke Behandlingstema for value=$value")
        }
    }
}
