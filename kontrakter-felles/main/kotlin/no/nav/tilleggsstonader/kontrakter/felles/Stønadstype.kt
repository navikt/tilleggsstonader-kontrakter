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
        visningsnavn = "støtte til daglige reiser",
        grunnlagAntallMånederBakITiden = 3,
    ),
    DAGLIG_REISE_TSR(
        visningsnavn = "støtte til daglige reiser",
        grunnlagAntallMånederBakITiden = 3,
    ),
    REISE_TIL_SAMLING_TSO(
        visningsnavn = "støtte ved reise til samling",
        grunnlagAntallMånederBakITiden = 6,
    ),
    REISE_TIL_SAMLING_TSR(
        visningsnavn = "støtte ved reise til samling",
        grunnlagAntallMånederBakITiden = 6,
    ),
    FLYTTING_TSO(
        visningsnavn = "støtte til flytting",
        grunnlagAntallMånederBakITiden = 6,
    ),
    FLYTTING_TSR(
        visningsnavn = "støtte til flytting",
        grunnlagAntallMånederBakITiden = 6,
    ),
    STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_HJEMREISE_TSO(
        visningsnavn = "støtte til reise ved oppstart, avslutning og hjemreise",
        grunnlagAntallMånederBakITiden = 6,
    ),
    STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_HJEMREISE_TSR(
        visningsnavn = "støtte til reise ved oppstart, avslutning og hjemreise",
        grunnlagAntallMånederBakITiden = 6,
    ),
}

fun Stønadstype.gjelderDagligReise() = Stønadstype.DAGLIG_REISE_TSO == this || Stønadstype.DAGLIG_REISE_TSR == this

fun Stønadstype.gjelderReiseTilSamling() = Stønadstype.REISE_TIL_SAMLING_TSO == this || Stønadstype.REISE_TIL_SAMLING_TSR == this

fun Stønadstype.gjelderFlytting() = Stønadstype.FLYTTING_TSO == this || Stønadstype.FLYTTING_TSR == this

fun Stønadstype.gjelderStøtteTilReiseOppstartAvslutningHjemreise() =
    Stønadstype.STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_HJEMREISE_TSO == this ||
        Stønadstype.STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_HJEMREISE_TSR == this

fun Stønadstype.behandlendeEnhet() =
    when (this) {
        Stønadstype.BARNETILSYN,
        Stønadstype.LÆREMIDLER,
        Stønadstype.BOUTGIFTER,
        Stønadstype.DAGLIG_REISE_TSO,
        Stønadstype.REISE_TIL_SAMLING_TSO,
        Stønadstype.REISE_TIL_SAMLING_TSR,
        Stønadstype.FLYTTING_TSO,
        Stønadstype.STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_HJEMREISE_TSO,
        -> Enhet.NAV_ARBEID_OG_YTELSER_TILLEGGSSTØNAD
        Stønadstype.DAGLIG_REISE_TSR,
        Stønadstype.FLYTTING_TSR,
        Stønadstype.STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_HJEMREISE_TSR,
        -> Enhet.NAV_TILTAK_OSLO
    }
