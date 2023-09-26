package no.nav.tilleggsstonader.kontrakter.søknad

import java.time.LocalDateTime

class Søknadsskjema<T>(
    val ident: String,
    val mottattTidspunkt: LocalDateTime,
    val skjema: T,
)
