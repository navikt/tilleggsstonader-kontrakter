package no.nav.tilleggsstonader.kontrakter.arena.vedtak

import no.nav.tilleggsstonader.kontrakter.arena.KodeArena
import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype

enum class UtfallVedtak(
    val navn: String,
) {
    NEI("Nei"),
    JA("Ja"),
    AVBRUTT("Avbrutt"),
}

enum class StatusVedtak(
    override val kodeArena: String,
    val navn: String,
) : KodeArena {
    GODKJENT("GODKJ", "Godkjent"),
    REGISTRERT("REGIS", "Registrert"),
    OPPRETTET("OPPRE", "Opprettet"),
    AVSLUTTET("AVSLU", "Avsluttet"),
    INNSTILT("INNST", "Innstilt"),
    IVERKSATT("IVERK", "Iverksatt"),
    MOTTATT("MOTAT", "Mottatt"),
}

enum class TypeVedtak(
    override val kodeArena: String,
    val navn: String,
) : KodeArena {
    ENDRING("E", "Endring"),
    NY_RETTIGHET("O", "Ny rettighet"),
    STANS("S", "Stans"),
}

enum class Rettighet(
    override val kodeArena: String,
    val navn: String,
    val stønadstype: Stønadstype? = null,
) : KodeArena {
    BOUTGIFTER_ARBEIDSSØKERE("TSRBOUTG", "Boutgifter arbeidssøkere", Stønadstype.BOUTGIFTER),
    BOUTGIFTER("TSOBOUTG", "Boutgifter tilleggsstønad", Stønadstype.BOUTGIFTER),
    DAGLIG_REISE_ARBEIDSSØKERE("TSRDAGREIS", "Daglig reise arbeidssøkere", Stønadstype.DAGLIG_REISE_TSR),
    DAGLIG_REISE("TSODAGREIS", "Daglig reise tilleggsstønad", Stønadstype.DAGLIG_REISE_TSO),
    FLYTTING_ARBEIDSSSØKERE("TSRFLYTT", "Flytting arbeidssøkere", Stønadstype.FLYTTING_TSR),
    FLYTTING("TSOFLYTT", "Flytting tilleggsstønad", Stønadstype.FLYTTING_TSO),
    LÆREMIDLER_ARBEIDSSSØKERE("TSRLMIDLER", "Læremidler arbeidssøkere", Stønadstype.LÆREMIDLER),
    LÆREMIDLER("TSOLMIDLER", "Læremidler tilleggsstønad", Stønadstype.LÆREMIDLER),
    REISE_OBLIGATORISK_SAMLING_ARBEIDSSSØKERE(
        "TSRREISOBL",
        "Reise til obligatorisk samling arbeidssøkere",
        Stønadstype.REISE_TIL_SAMLING_TSR,
    ),
    REISE_OBLIGATORISK_SAMLING("TSOREISOBL", "Reise til obligatorisk samling tilleggsstønad", Stønadstype.REISE_TIL_SAMLING_TSO),
    REISE_AKTIVITET_HJEMREISE_ARBEIDSSSØKERE(
        "TSRREISAKT",
        "Reise ved start/slutt aktivitet og hjemreiser arbeidssøkere",
        Stønadstype.STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_HJEMREISE_TSR,
    ),
    REISE_AKTIVITET_HJEMREISE(
        "TSOREISAKT",
        "Reise ved start/slutt aktivitet og hjemreiser tilleggsstønad",
        Stønadstype.STØTTE_TIL_REISE_OPPSTART_AVSLUTNING_HJEMREISE_TSO,
    ),
    REISE_ARBEIDSSSØKERE("TSRREISARB", "Reisestønader til arbeidssøkere"),
    REISE("TSOREISARB", "Reisestønader til arbeidssøkere tilleggsstønad"),
    PASS_AV_BARN_ARBEIDSSSØKERE("TSRTILBARN", "Pass av barn arbeidssøkere", Stønadstype.BARNETILSYN),
    PASS_AV_BARN("TSOTILBARN", "Pass av barn tilleggsstønad", Stønadstype.BARNETILSYN),
    TILSYN_FAMILIEMEDLEMMER_ARBEIDSSSØKERE("TSRTILFAM", "Tilsyn av familiemedlemmer arbeidssøkere"),
    TILSYN_FAMILIEMEDLEMMER("TSOTILFAM", "Tilsyn av familiemedlemmer tilleggsstønad"),
    ;

    fun stønadstypeEllerFeil(): Stønadstype = stønadstype ?: error("Har ikke lagt inn mapping av stønadstype for $this")

    companion object {
        private val rettighetPaKodeArena = entries.associateBy { it.kodeArena }

        fun fraKodeArena(kodeArena: String): Rettighet =
            rettighetPaKodeArena[kodeArena]
                ?: error("Finner ikke mapping for $kodeArena")

        private val rettigheterPerStønadstype =
            entries
                .mapNotNull { rettighet -> rettighet.stønadstype?.let { it to rettighet } }
                .groupBy({ it.first }, { it.second })

        fun fraStønadstype(stønadstype: Stønadstype): List<Rettighet> =
            rettigheterPerStønadstype[stønadstype] ?: error("Finner ikke mapping for $stønadstype")

        fun Set<Stønadstype>.tilArenaKoder() = this.flatMap { Rettighet.fraStønadstype(it) }.map { it.kodeArena }
    }
}
