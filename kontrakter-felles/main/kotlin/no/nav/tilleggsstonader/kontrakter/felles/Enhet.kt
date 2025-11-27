package no.nav.tilleggsstonader.kontrakter.felles

enum class Enhet(
    val enhetsnr: String,
) {
    NAV_ARBEID_OG_YTELSER_TILLEGGSSTØNAD("4462"),
    NAV_ARBEID_OG_YTELSER_EGNE_ANSATTE("4483"),
    NAV_TILTAK_OSLO("0387"),
    VIKAFOSSEN("2103"),
}
