package no.nav.tilleggsstonader.kontrakter.felles

/**
 * @param tittel kan eks brukes i brev
 */
enum class Stønadstype(val tittel: String) {
    BARNETILSYN("Barnetilsyn"),
    LÆREMIDLER("Læremidler"),
}
