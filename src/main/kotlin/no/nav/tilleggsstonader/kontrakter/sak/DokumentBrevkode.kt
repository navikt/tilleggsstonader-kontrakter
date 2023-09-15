package no.nav.tilleggsstonader.kontrakter.sak

enum class DokumentBrevkode(val verdi: String) {
    BARNETILSYN("NAV 15-00.02"), // TODO oppdater
    ;

    companion object {
        fun erGyldigBrevkode(brevKode: String?): Boolean = values().any { it.verdi == brevKode }
        fun fraBrevkode(brevKode: String?): DokumentBrevkode = values().first { it.verdi == brevKode }
    }
}
