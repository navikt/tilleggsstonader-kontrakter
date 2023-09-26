package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.felles.Ident
import java.time.LocalDateTime

class Søknadsskjema<T>(
    val ident: Ident,
    val mottattTidspunkt: LocalDateTime,
    val skjema: T,
)
