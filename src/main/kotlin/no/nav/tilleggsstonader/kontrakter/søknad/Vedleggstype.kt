package no.nav.tilleggsstonader.kontrakter.søknad

/**
 * @param tittel brukes som tittel for vedlegget i dokarkiv, sånn at det vises i gosys
 */
enum class Vedleggstype(val tittel: String) { // TODO språk?
    // Pass av barn
    UTGIFTER_PASS_SFO_AKS_BARNEHAGE("Dokumentasjon av utgifter for pass av barn"),
    UTGIFTER_PASS_ANNET("Dokumentasjon av utgifter for pass av barn"),

    // Årsak ekstra pass
    SKRIFTLIG_UTTALELSE_HELSEPERSONELL("Dokumentasjon av uttalelse fra helsepersonell"),
    TILTAKSSTED_ELLER_UTDANNINGSSTED("Dokumentasjon fra tiltakssted eller utdanningssted"),

    @Deprecated("Bruk SKRIFTLIG_UTTALELSE_HELSEPERSONELL/TILTAKSSTED_ELLER_UTDANNINGSSTED")
    EKSTRA_PASS_BEHOV("Dokumentasjon på behov for ekstra pass av barn"),
}
