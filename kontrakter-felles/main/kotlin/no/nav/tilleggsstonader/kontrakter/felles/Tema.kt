package no.nav.tilleggsstonader.kontrakter.felles

/**
 * Vi har valgt å kun bruke [TSO] for nye søknader
 * TODO - Gjelder dette fortsatt eller skal vi bruke TSR for Tiltak som tidligere?
 * [TSO] historiskt ble denne brukt for NAY
 * [TSR] historiskt ble denne brukt for Tiltak
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
