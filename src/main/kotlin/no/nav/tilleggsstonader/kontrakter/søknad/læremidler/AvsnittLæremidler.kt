package no.nav.tilleggsstonader.kontrakter.søknad.læremidler

import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei

data class UtdanningAvsnitt(
    val annenUtdanning: EnumFelt<AnnenUtdanningType>? = null,
    val mottarUtstyrsstipend: EnumFelt<JaNei>? = null,
    val harFunksjonsnedsettelse: EnumFelt<JaNei>,
)

enum class AnnenUtdanningType {
    VIDEREGÅENDE_FORKURS,
    FAGSKOLE_HØGSKOLE_UNIVERSITET,
    KURS_LIKNENDE,
    INGEN_UTDANNING,
}
