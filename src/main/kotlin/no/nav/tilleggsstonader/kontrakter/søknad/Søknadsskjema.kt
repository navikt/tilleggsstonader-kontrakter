package no.nav.tilleggsstonader.kontrakter.søknad

import java.time.LocalDateTime

class Søknadsskjema<T>(
    val personIdent: String,
    val mottattTidspunkt: LocalDateTime,
    val skjema: T,
)
