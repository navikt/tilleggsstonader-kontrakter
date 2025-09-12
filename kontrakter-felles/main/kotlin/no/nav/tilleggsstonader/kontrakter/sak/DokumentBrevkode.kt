package no.nav.tilleggsstonader.kontrakter.sak

enum class DokumentBrevkode(
    val verdi: String,
) {
    BARNETILSYN("NAV 11-12.15"),
    LÆREMIDLER("NAV 11-12.16"),
    BOUTGIFTER("NAV 11-12.19"),
    DAGLIG_REISE("NAV 11-12.21"),
    ;

    companion object {
        fun erGyldigBrevkode(brevKode: String?): Boolean = entries.any { it.verdi == brevKode }

        fun fraBrevkode(brevKode: String?): DokumentBrevkode? = entries.firstOrNull { it.verdi == brevKode }
    }
}
