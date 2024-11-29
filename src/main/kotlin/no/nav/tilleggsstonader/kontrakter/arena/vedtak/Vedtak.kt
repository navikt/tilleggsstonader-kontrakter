package no.nav.tilleggsstonader.kontrakter.arena.vedtak

import no.nav.tilleggsstonader.kontrakter.arena.KodeArena
import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype

enum class UtfallVedtak(val navn: String) {
    NEI("Nei"),
    JA("Ja"),
    AVBRUTT("Avbrutt"),
}

enum class StatusVedtak(override val kodeArena: String, val navn: String) : KodeArena {
    GODKJENT("GODKJ", "Godkjent"),
    REGISTRERT("REGIS", "Registrert"),
    OPPRETTET("OPPRE", "Opprettet"),
    AVSLUTTET("AVSLU", "Avsluttet"),
    INNSTILT("INNST", "Innstilt"),
    IVERKSATT("IVERK", "Iverksatt"),
    MOTTATT("MOTAT", "Mottatt"),
}

enum class TypeVedtak(override val kodeArena: String, val navn: String) : KodeArena {
    ENDRING("E", "Endring"),
    NY_RETTIGHET("O", "Ny rettighet"),
    STANS("S", "Stans"),
}

enum class Rettighet(
    override val kodeArena: String,
    val navn: String,
    internal val type: Stønadstype? = null,
) : KodeArena {
    BOUTGIFTER_ARBEIDSSØKERE("TSRBOUTG", "Boutgifter arbeidssøkere"),
    BOUTGIFTER("TSOBOUTG", "Boutgifter tilleggsstønad"),
    DAGLIG_REISE_ARBEIDSSØKERE("TSRDAGREIS", "Daglig reise arbeidssøkere"),
    DAGLIG_REISE("TSODAGREIS", "Daglig reise tilleggsstønad"),
    FLYTTING_ARBEIDSSSØKERE("TSRFLYTT", "Flytting arbeidssøkere"),
    FLYTTING("TSOFLYTT", "Flytting tilleggsstønad"),
    LÆREMIDLER_ARBEIDSSSØKERE("TSRLMIDLER", "Læremidler arbeidssøkere", Stønadstype.LÆREMIDLER),
    LÆREMIDLER("TSOLMIDLER", "Læremidler tilleggsstønad", Stønadstype.LÆREMIDLER),
    REISE_OBLIGATORISK_SAMLING_ARBEIDSSSØKERE("TSRREISOBL", "Reise til obligatorisk samling arbeidssøkere"),
    REISE_OBLIGATORISK_SAMLING("TSOREISOBL", "Reise til obligatorisk samling tilleggsstønad"),
    REISE_AKTIVITET_HJEMREISE_ARBEIDSSSØKERE(
        "TSRREISAKT",
        "Reise ved start/slutt aktivitet og hjemreiser arbeidssøkere",
    ),
    REISE_AKTIVITET_HJEMREISE("TSOREISAKT", "Reise ved start/slutt aktivitet og hjemreiser tilleggsstønad"),
    REISE_ARBEIDSSSØKERE("TSRREISARB", "Reisestønader til arbeidssøkere"),
    REISE("TSOREISARB", "Reisestønader til arbeidssøkere tilleggsstønad"),
    TILSYN_BARN_ARBEIDSSSØKERE("TSRTILBARN", "Tilsyn av barn arbeidssøkere", Stønadstype.BARNETILSYN),
    TILSYN_BARN("TSOTILBARN", "Tilsyn av barn tilleggsstønad", Stønadstype.BARNETILSYN),
    TILSYN_FAMILIEMEDLEMMER_ARBEIDSSSØKERE("TSRTILFAM", "Tilsyn av familiemedlemmer arbeidssøkere"),
    TILSYN_FAMILIEMEDLEMMER("TSOTILFAM", "Tilsyn av familiemedlemmer tilleggsstønad"),
    ;

    val stønadstype get(): Stønadstype = type ?: error("Har ikke lagt inn mapping av stønadstype for $this")

    companion object {

        val rettighetPaKodeArena = entries.associateBy { it.kodeArena }
        fun fraKodeArena(kodeArena: String): Rettighet {
            return rettighetPaKodeArena[kodeArena]
                ?: error("Finner ikke mapping for $kodeArena")
        }
    }
}
