package no.nav.tilleggsstonader.kontrakter.felles

/**
 * @param visningsnavn kan eks brukes i brev
 * @param grunnlagAntallMånederBakITiden brukes for å vite hvor langt tilbake i tiden man skal hente grunnlag for
 */
enum class Stønadstype(
    val visningsnavn: String,
    val grunnlagAntallMånederBakITiden: Int,
) {
    BARNETILSYN(
        visningsnavn = "stønad til pass av barn",
        grunnlagAntallMånederBakITiden = 3,
    ),
    LÆREMIDLER(
        visningsnavn = "støtte til læremidler",
        grunnlagAntallMånederBakITiden = 6,
    ),
}
