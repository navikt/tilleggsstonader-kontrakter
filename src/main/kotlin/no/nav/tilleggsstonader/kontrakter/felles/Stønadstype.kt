package no.nav.tilleggsstonader.kontrakter.felles

/**
 * @param tittel kan eks brukes i brev
 * @param grunnlagAntallMånederBakITiden brukes for å vite hvor langt tilbake i tiden man skal hente grunnlag for
 */
enum class Stønadstype(
    val tittel: String,
    val grunnlagAntallMånederBakITiden: Int,
) {
    BARNETILSYN(tittel = "Barnetilsyn", grunnlagAntallMånederBakITiden = 3),
    LÆREMIDLER(tittel = "Læremidler", grunnlagAntallMånederBakITiden = 6),
}
