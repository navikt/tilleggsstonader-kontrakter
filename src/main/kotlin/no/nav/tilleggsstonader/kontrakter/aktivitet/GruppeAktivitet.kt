package no.nav.tilleggsstonader.kontrakter.aktivitet

/**
 * Aktivitetsgrupper. Alle grupper er ikke aktuellt å vise i saksbehandlingen
 *
 */
enum class GruppeAktivitet(val beskrivelse: String) {
    A14A("Aktiviteter ifm $14a"),
    ADMO("Aktiviteter i fbm dialogmøte"),
    ANN("Andre aktiviteter"),
    ARK("ARK Tjenester"),
    EGEN("Egenaktivitet"),
    IAA("IA Aktiviteter"),
    SAK("Saksbehandlingsaktivitet"),
    SPES("Spesialaktivitet"),
    SPES2("Spesialaktiviteter"),
    TLTAK("Tiltaksaktiviteter"),
    VEIL("Gruppeaktivitet"),
}
