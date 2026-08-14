package no.nav.tilleggsstonader.kontrakter.felles

enum class Skjematype {
    SØKNAD_BARNETILSYN,
    SØKNAD_LÆREMIDLER,
    SØKNAD_BOUTGIFTER,
    SØKNAD_DAGLIG_REISE,
    DAGLIG_REISE_KJØRELISTE,
    SØKNAD_REISE_TIL_SAMLING,
    SØKNAD_FLYTTING,
    ;

    fun erSøknad() =
        this in
            listOf(SØKNAD_BARNETILSYN, SØKNAD_LÆREMIDLER, SØKNAD_BOUTGIFTER, SØKNAD_DAGLIG_REISE, SØKNAD_REISE_TIL_SAMLING, SØKNAD_FLYTTING)
}

fun Stønadstype.tilSkjematype() =
    when (this) {
        Stønadstype.BARNETILSYN -> Skjematype.SØKNAD_BARNETILSYN
        Stønadstype.LÆREMIDLER -> Skjematype.SØKNAD_LÆREMIDLER
        Stønadstype.BOUTGIFTER -> Skjematype.SØKNAD_BOUTGIFTER
        Stønadstype.DAGLIG_REISE_TSO, Stønadstype.DAGLIG_REISE_TSR -> Skjematype.SØKNAD_DAGLIG_REISE
        Stønadstype.REISE_TIL_SAMLING_TSO -> Skjematype.SØKNAD_REISE_TIL_SAMLING
        Stønadstype.FLYTTING_TSO, Stønadstype.FLYTTING_TSR -> Skjematype.SØKNAD_FLYTTING
    }

fun Skjematype.tilStønadstyper(): Set<Stønadstype> =
    when (this) {
        Skjematype.SØKNAD_DAGLIG_REISE, Skjematype.DAGLIG_REISE_KJØRELISTE ->
            setOf(
                Stønadstype.DAGLIG_REISE_TSO,
                Stønadstype.DAGLIG_REISE_TSR,
            )

        Skjematype.SØKNAD_BOUTGIFTER -> setOf(Stønadstype.BOUTGIFTER)
        Skjematype.SØKNAD_LÆREMIDLER -> setOf(Stønadstype.LÆREMIDLER)
        Skjematype.SØKNAD_BARNETILSYN -> setOf(Stønadstype.BARNETILSYN)
        Skjematype.SØKNAD_REISE_TIL_SAMLING -> setOf(Stønadstype.REISE_TIL_SAMLING_TSO)
        Skjematype.SØKNAD_FLYTTING -> setOf(Stønadstype.FLYTTING_TSO, Stønadstype.FLYTTING_TSR)
    }
