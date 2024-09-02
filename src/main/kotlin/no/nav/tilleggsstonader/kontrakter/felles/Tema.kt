package no.nav.tilleggsstonader.kontrakter.felles

/**
 * Vi har valgt å kun bruke [TSO] for nye søknader
 * [TSO] historiskt ble denne brukt for NAY
 * [TSR] historiskt ble denne brukt for Tiltak
 */
enum class Tema {
    TSO,
    TSR,
    ;

    fun default(): Tema = TSO
}

fun Stønadstype.tilTema(): Tema = when (this) {
    Stønadstype.BARNETILSYN -> Tema.TSO
    Stønadstype.LÆREMIDLER -> Tema.TSO
}
