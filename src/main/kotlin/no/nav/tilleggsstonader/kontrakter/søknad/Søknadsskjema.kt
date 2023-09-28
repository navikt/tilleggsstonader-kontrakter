package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import java.time.LocalDateTime

class Søknadsskjema<T>(
    val ident: String,
    val mottattTidspunkt: LocalDateTime,
    val språk: Språkkode,
    val skjema: T,
)
