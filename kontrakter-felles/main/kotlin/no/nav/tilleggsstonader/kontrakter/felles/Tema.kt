package no.nav.tilleggsstonader.kontrakter.felles

/**
 * Brukes i Nav for å klassifisere dokumenter og oppgaver etter fagområde eller forretningsfunksjon på tvers av fagområder
 */
enum class Tema {
    TSO,
    TSR,
    ;

    companion object {
        fun gjelderTemaTilleggsstønader(tema: String?) =
            TSO.name.equals(tema, ignoreCase = true) ||
                TSR.name.equals(tema, ignoreCase = true)
    }
}

fun Stønadstype.tilTema(): Tema =
    when (this) {
        Stønadstype.BARNETILSYN -> Tema.TSO
        Stønadstype.LÆREMIDLER -> Tema.TSO
        Stønadstype.BOUTGIFTER -> Tema.TSO
        Stønadstype.DAGLIG_REISE_TSO -> Tema.TSO
        Stønadstype.DAGLIG_REISE_TSR -> Tema.TSR
    }
