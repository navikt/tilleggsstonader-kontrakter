package no.nav.tilleggsstonader.kontrakter.søknad

/**
 * @param tittel brukes som tittel for vedlegget i dokarkiv, sånn at det vises i gosys
 */
enum class Vedleggstype(val tittel: String) { // TODO språk?
    EKSEMPEL("Eksempel"), // TODO Denne kan slettes, kun brukt for å sette opp tester

    UTGIFTER_PASS_SFO_AKS_BARNEHAGE("Dokumentasjon av utgifter for pass av barn"),
    UTGIFTER_PASS_ANNET("Dokumentasjon av utgifter for pass av barn"),
    EKSTRA_PASS_BEHOV("Dokumentasjon på behov for ekstra pass av barn"),
}
