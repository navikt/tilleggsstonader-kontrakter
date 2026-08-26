package no.nav.tilleggsstonader.kontrakter.sak

import no.nav.tilleggsstonader.kontrakter.felles.Skjematype

enum class DokumentBrevkode(
    val verdi: String,
) {
    PASS_AV_BARN("NAV 11-12.15"),
    PASS_AV_BARN_GAMMEL("NAV 11-12.15B"),
    LÆREMIDLER("NAV 11-12.16"),
    LÆREMIDLER_GAMMEL("NAV 11-12.16B"),
    BOUTGIFTER("NAV 11-12.19"),
    BOUTGIFTER_GAMMEL("NAV 11-12.19B"),
    REISE_TIL_SAMLING("NAV 11-12.17"),
    REISE_TIL_SAMLING_GAMMEL("NAV 11-12.17B"),
    STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_ELLER_HJEMREISE("NAV 11-12.18"),
    STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_ELLER_HJEMREISE_GAMMEL("NAV 11-12.18B"),
    DAGLIG_REISE("NAV 11-12.21"),
    DAGLIG_REISE_GAMMEL("NAV 11-12.21B"),
    REISE_FOR_Å_KOMME_I_ARBEID("NAV 11-12.22"),
    REISE_FOR_Å_KOMME_I_ARBEID_GAMMEL("NAV 11-12.22B"),
    FLYTTING("NAV 11-12.23"),
    FLYTTING_GAMMEL("NAV 11-12.23B"),
    DAGLIG_REISE_KJØRELISTE("NAV 11-12.24"),
    DAGLIG_REISE_KJØRELISTE_GAMMEL("NAV 11-12.24B"),
    ;

    companion object {
        fun erGyldigBrevkode(brevKode: String?): Boolean = entries.any { it.verdi == brevKode }

        fun fraBrevkode(brevKode: String?): DokumentBrevkode? = entries.firstOrNull { it.verdi == brevKode }
    }

    fun tilSkjematype(): Skjematype? =
        when (this) {
            PASS_AV_BARN -> Skjematype.SØKNAD_BARNETILSYN
            LÆREMIDLER -> Skjematype.SØKNAD_LÆREMIDLER
            BOUTGIFTER -> Skjematype.SØKNAD_BOUTGIFTER
            DAGLIG_REISE -> Skjematype.SØKNAD_DAGLIG_REISE
            DAGLIG_REISE_KJØRELISTE -> Skjematype.DAGLIG_REISE_KJØRELISTE
            REISE_TIL_SAMLING -> Skjematype.SØKNAD_REISE_TIL_SAMLING
            STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_ELLER_HJEMREISE -> Skjematype.SØKNAD_STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_HJEMREISE
            FLYTTING -> Skjematype.SØKNAD_FLYTTING
            else -> null
        }
}
