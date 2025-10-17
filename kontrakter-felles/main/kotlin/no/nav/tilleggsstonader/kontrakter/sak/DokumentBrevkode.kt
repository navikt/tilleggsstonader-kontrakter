package no.nav.tilleggsstonader.kontrakter.sak

import no.nav.tilleggsstonader.kontrakter.felles.Skjematype

enum class DokumentBrevkode(
    val verdi: String,
) {
    BARNETILSYN("NAV 11-12.15"),
    BARNETILSYN_GAMMEL("NAV 11-12.15B"),
    LÆREMIDLER("NAV 11-12.16"),
    LÆREMIDLER_GAMMEL("NAV 11-12.16B"),
    BOUTGIFTER("NAV 11-12.19"),
    BOUTGIFTER_GAMMEL("NAV 11-12.19B"),
    DAGLIG_REISE("NAV 11-12.21"),
    DAGLIG_REISE_GAMMEL("NAV 11-12.21B"),
    DAGLIG_REISE_KJØRELISTE("NAV 11-12.24"),
    DAGLIG_REISE_KJØRELISTE_GAMMEL("NAV 11-12.24B"),
    ;

    companion object {
        fun erGyldigBrevkode(brevKode: String?): Boolean = entries.any { it.verdi == brevKode }

        fun fraBrevkode(brevKode: String?): DokumentBrevkode? = entries.firstOrNull { it.verdi == brevKode }
    }

    fun tilSkjematype(): Skjematype? =
        when (this) {
            BARNETILSYN -> Skjematype.SØKNAD_BARNETILSYN
            LÆREMIDLER -> Skjematype.SØKNAD_LÆREMIDLER
            BOUTGIFTER -> Skjematype.SØKNAD_BOUTGIFTER
            DAGLIG_REISE -> Skjematype.SØKNAD_DAGLIG_REISE
            DAGLIG_REISE_KJØRELISTE -> Skjematype.DAGLIG_REISE_KJØRELISTE
            else -> null
        }
}
