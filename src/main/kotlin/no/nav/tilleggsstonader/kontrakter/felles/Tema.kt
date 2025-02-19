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

    fun default(): Tema = TSO
}

fun Stønadstype.tilTema(): Tema =
    when (this) {
        Stønadstype.BARNETILSYN -> Tema.TSO
        Stønadstype.LÆREMIDLER -> Tema.TSO
        Stønadstype.BOUTGIFTER -> Tema.TSO
    }
