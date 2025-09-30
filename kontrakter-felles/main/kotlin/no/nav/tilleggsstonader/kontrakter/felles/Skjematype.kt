package no.nav.tilleggsstonader.kontrakter.felles

enum class Skjematype {
    BARNETILSYN,
    LÆREMIDLER,
    BOUTGIFTER,
    DAGLIG_REISE,
}

fun Stønadstype.tilSkjematype() =
    when (this) {
        Stønadstype.BARNETILSYN -> Skjematype.BARNETILSYN
        Stønadstype.LÆREMIDLER -> Skjematype.LÆREMIDLER
        Stønadstype.BOUTGIFTER -> Skjematype.BOUTGIFTER
        Stønadstype.DAGLIG_REISE_TSO, Stønadstype.DAGLIG_REISE_TSR -> Skjematype.DAGLIG_REISE
    }

fun Skjematype.tilStønadstyper(): Set<Stønadstype> =
    when (this) {
        Skjematype.DAGLIG_REISE ->
            setOf(
                Stønadstype.DAGLIG_REISE_TSO,
                Stønadstype.DAGLIG_REISE_TSR,
            )

        Skjematype.BOUTGIFTER -> setOf(Stønadstype.BOUTGIFTER)
        Skjematype.LÆREMIDLER -> setOf(Stønadstype.LÆREMIDLER)
        Skjematype.BARNETILSYN -> setOf(Stønadstype.BARNETILSYN)
    }
