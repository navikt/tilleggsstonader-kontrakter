package no.nav.tilleggsstonader.kontrakter.felles

/**
 * Kan brukes til oppslag på ident og gitt stønadstype
 */
data class IdentStønadstype(
    val ident: String,
    val stønadstype: Stønadstype,
)
