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
    BOUTGIFTER(
        visningsnavn = "støtte til bolig eller overnatting",
        grunnlagAntallMånederBakITiden = 6,
    ),
    DAGLIG_REISE_TSO(
        visningsnavn = "støtte til daglig reise",
        grunnlagAntallMånederBakITiden = 3,
    ),
    DAGLIG_REISE_TSR(
        visningsnavn = "støtte til daglig reise",
        grunnlagAntallMånederBakITiden = 3,
    ),
}
