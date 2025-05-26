package no.nav.tilleggsstonader.kontrakter.sak

import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype

enum class DokumentBrevkode(
    val verdi: String,
    val stønadstype: Stønadstype,
) {
    BARNETILSYN("NAV 11-12.15", Stønadstype.BARNETILSYN),
    LÆREMIDLER("NAV 11-12.16", Stønadstype.LÆREMIDLER),
    BOUTGIFTER("NAV 11-12.19", Stønadstype.BOUTGIFTER),
    ;

    companion object {
        fun erGyldigBrevkode(brevKode: String?): Boolean = entries.any { it.verdi == brevKode }

        fun fraBrevkode(brevKode: String?): DokumentBrevkode? = entries.firstOrNull { it.verdi == brevKode }
    }
}
