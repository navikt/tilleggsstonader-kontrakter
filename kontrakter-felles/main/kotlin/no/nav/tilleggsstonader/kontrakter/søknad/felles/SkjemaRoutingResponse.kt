package no.nav.tilleggsstonader.kontrakter.søknad.felles

enum class SkjemaRoutingAksjon {
    NY_LØSNING,
    GAMMEL_LØSNING,

    @Deprecated("Bruk AVSJEKK_OFFENTLIG_TRANSPORT", replaceWith = ReplaceWith("AVSJEKK_OFFENTLIG_TRANSPORT"))
    AVSJEKK,
    AVSJEKK_OFFENTLIG_TRANSPORT,
    AVSJEKK_TAXI,
}

data class SkjemaRoutingResponse(
    val aksjon: SkjemaRoutingAksjon,
)
