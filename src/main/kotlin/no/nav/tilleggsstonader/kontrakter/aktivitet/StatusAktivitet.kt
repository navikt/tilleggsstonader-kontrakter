package no.nav.tilleggsstonader.kontrakter.aktivitet

/**
 * Statuser mappet fra statuser i arena i integrasjoner
 * [no.nav.tilleggsstonader.integrasjoner.arena.dto.StatusAktivitetArena]
 */
enum class StatusAktivitet(
    val beskrivelse: String,
    val rettTilÅSøke: Boolean,
) {
    VENTER_PA_OPPSTART("Venter på oppstart", true),
    AKTUELL("Aktuell", true),
    DELTAR("Deltar", true),
    AVBRUTT("Avbrutt", true),
    FULLFØRT("Fullført", true),
    OPPHØRT("Opphørt", true),
    OVERFØRT("Overført", true),
    BEHOV("Behov", true),

    IKKE_AKTUELL("Ikke aktuell", false),
    FEILREGISTRERT("Feilregistrert", false),
    PLANLAGT("Planlagt", false),
    VENTELISTE("Venteliste", false),
}
