package no.nav.tilleggsstonader.kontrakter.sak

enum class DokumentBrevkode(
    val verdi: String,
) {
    BARNETILSYN("NAV 11-12.15"),
    BARNETILSYN_GAMMEL("NAV 11-12.15b"),
    LÆREMIDLER("NAV 11-12.16"),
    LÆREMIDLER_GAMMEL("NAV 11-12.16b"),
    BOUTGIFTER("NAV 11-12.19"),
    BOUTGIFTER_GAMMEL("NAV 11-12.19b"),
    DAGLIG_REISE("NAV 11-12.21"),
    DAGLIG_REISE_GAMMEL("NAV 11-12.21b"),
    DAGLIG_REISE_KJØRELISTE("NAV 11-12.24"),
    DAGLIG_REISE_KJØRELISTE_GAMMEL("NAV 11-12.24b"),
    ;

    companion object {
        fun erGyldigBrevkode(brevKode: String?): Boolean = entries.any { it.verdi == brevKode }

        fun fraBrevkode(brevKode: String?): DokumentBrevkode? = entries.firstOrNull { it.verdi == brevKode }
    }
}
