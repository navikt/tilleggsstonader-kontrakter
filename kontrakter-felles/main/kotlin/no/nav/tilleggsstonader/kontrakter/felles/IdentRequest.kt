package no.nav.tilleggsstonader.kontrakter.felles

/**
 * Kan brukes for oppslag på ident, med post
 */
data class IdentRequest(
    val ident: String,
)

/**
 * Brukes på oppslag med ident og skjematype, eksempelvis nyttig når soknad-api skal snakke med sak
 */
data class IdentSkjematype(
    val ident: String,
    val skjematype: Skjematype,
)

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
data class IdenterRequest(
    val identer: Set<String>,
)

/**
 * Ved oppslag mot arena burde vi sende alle identene til en person,
 * i tilfelle identen har blitt oppdatert i PDL men ikke i Arena
 */
data class IdenterStønadstype(
    val identer: Set<String>,
    val stønadstype: Stønadstype,
)

/**
 * Ved oppslag mot arena burde vi sende alle identene til en person,
 * i tilfelle identen har blitt oppdatert i PDL men ikke i Arena
 */
data class IdenterStønadstyper(
    val identer: Set<String>,
    val stønadstyper: Set<Stønadstype>,
)
