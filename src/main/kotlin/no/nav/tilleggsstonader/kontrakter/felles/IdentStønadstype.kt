package no.nav.tilleggsstonader.kontrakter.felles

/**
 * Kan brukes til oppslag på ident og gitt stønadstype
 */
data class IdentStønadstype(
    val ident: String,
    val stønadstype: Stønadstype,
)

/**
 * Ved oppslag mot arena burde vi sende alle identene til en person,
 * i tilfelle identen har blitt oppdatert i PDL men ikke i Arena
 */
data class IdenterStønadstype(
    val identer: Set<String>,
    val stønadstype: Stønadstype,
)
