package no.nav.tilleggsstonader.kontrakter.felles

enum class Enhet(
    val enhetsnr: String,
) {
    NAV_ARBEID_OG_YTELSER_TILLEGGSSTØNAD("4462"),
    NAV_ARBEID_OG_YTELSER_EGNE_ANSATTE("4483"),
    NAV_TILTAK_OSLO("0387"),
    NAV_EGNE_ANSATTE_OSLO("0383"),
    VIKAFOSSEN("2103"),
    ;

    companion object {
        fun fraEnhetsnr(enhetsnr: String) =
            entries.firstOrNull { it.enhetsnr == enhetsnr } ?: error("Finner ikke enhet med enhetsnr $enhetsnr")
    }
}
