package no.nav.tilleggsstonader.kontrakter.søknad.felles

enum class SkjemaRoutingAksjon {
    NY_LØSNING,
    GAMMEL_LØSNING,
    AVSJEKK,
}

data class SkjemaRoutingResponse(
    val aksjon: SkjemaRoutingAksjon,
)
