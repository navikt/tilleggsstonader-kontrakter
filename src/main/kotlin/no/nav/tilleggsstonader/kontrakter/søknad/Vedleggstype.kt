package no.nav.tilleggsstonader.kontrakter.søknad

/**
 * @param tittel brukes som tittel for vedlegget i dokarkiv, sånn at det vises i gosys
 */
enum class Vedleggstype(
    val tittel: String,
) {
    // TODO språk?
    // Pass av barn
    UTGIFTER_PASS_SFO_AKS_BARNEHAGE("Dokumentasjon av utgifter for pass av barn"),
    UTGIFTER_PASS_PRIVAT("Dokumentasjon av utgifter til privat pass"),

    // Årsak ekstra pass
    SKRIFTLIG_UTTALELSE_HELSEPERSONELL("Dokumentasjon av uttalelse fra helsepersonell"),
    TILTAKSSTED_ELLER_UTDANNINGSSTED("Dokumentasjon fra tiltakssted eller utdanningssted"),

    // Læremidler
    DOKUMENTASJON_FUNKSJONSNEDSETTELSE("Dokumentasjon på funksjonsnedsettelse"),
    UTGIFTER_LÆREMIDLER("Dokumentasjon på utgifter til læremidler"),
}
