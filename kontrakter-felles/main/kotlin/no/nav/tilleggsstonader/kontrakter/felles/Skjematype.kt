package no.nav.tilleggsstonader.kontrakter.felles

enum class Skjematype {
    BARNETILSYN,
    SØKNAD_BARNETILSYN,
    LÆREMIDLER,
    SØKNAD_LÆREMIDLER,
    BOUTGIFTER,
    SØKNAD_BOUTGIFTER,
    DAGLIG_REISE,
    SØKNAD_DAGLIG_REISE,
    DAGLIG_REISE_KJØRELISTE,
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
        Skjematype.DAGLIG_REISE, Skjematype.SØKNAD_DAGLIG_REISE, Skjematype.DAGLIG_REISE_KJØRELISTE ->
            setOf(
                Stønadstype.DAGLIG_REISE_TSO,
                Stønadstype.DAGLIG_REISE_TSR,
            )

        Skjematype.BOUTGIFTER, Skjematype.SØKNAD_BOUTGIFTER -> setOf(Stønadstype.BOUTGIFTER)
        Skjematype.LÆREMIDLER, Skjematype.SØKNAD_LÆREMIDLER -> setOf(Stønadstype.LÆREMIDLER)
        Skjematype.BARNETILSYN, Skjematype.SØKNAD_BARNETILSYN -> setOf(Stønadstype.BARNETILSYN)
    }
