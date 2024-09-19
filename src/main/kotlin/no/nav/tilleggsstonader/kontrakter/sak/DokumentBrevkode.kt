package no.nav.tilleggsstonader.kontrakter.sak

enum class DokumentBrevkode(val verdi: String) {
    BARNETILSYN("NAV 11-12.15"),
    LÆREMIDLER("NAV 11-12.16"),
    ;

    companion object {
        fun erGyldigBrevkode(brevKode: String?): Boolean = values().any { it.verdi == brevKode }
        fun fraBrevkode(brevKode: String?): DokumentBrevkode = values().first { it.verdi == brevKode }
    }
}
