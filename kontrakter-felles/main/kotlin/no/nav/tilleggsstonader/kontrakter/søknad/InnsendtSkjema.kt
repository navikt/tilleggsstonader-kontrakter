package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import java.time.LocalDateTime

data class InnsendtSkjema<T : Skjemadata>(
    val ident: String,
    val mottattTidspunkt: LocalDateTime,
    val språk: Språkkode,
    val skjema: T,
)

sealed interface Skjemadata {
    val dokumentasjon: List<DokumentasjonFelt>
}
