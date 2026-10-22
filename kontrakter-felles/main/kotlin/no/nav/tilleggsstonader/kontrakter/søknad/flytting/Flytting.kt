package no.nav.tilleggsstonader.kontrakter.søknad.flytting

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.VerdiFelt
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.Adresse

enum class Flytteårsak {
    NY_JOBB,
    AKTIVITET,
}

enum class Flyttemåte {
    FLYTTER_SELV,
    FLYTTEBYRÅ,
}

enum class ValgtTilbud {
    TILBUD_1,
    TILBUD_2,
}

data class FlyttingDetaljer(
    val årsak: EnumFelt<Flytteårsak>?,
    val flyttedato: VerdiFelt<String>?,
    val førsteArbeidsdag: VerdiFelt<String>?,
    val adresse: Adresse?,
    val flyttemåte: EnumFelt<Flyttemåte>?,
    val utgifterDekketPåAnnenMåte: EnumFelt<JaNei>?,
    val utgifterEgenFlytting: UtgifterEgenFlytting?,
    val flyttebyrå: Flyttebyrå?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Detaljer om flyttingen",
        )
}

data class UtgifterEgenFlytting(
    val avstand: VerdiFelt<String>?,
    val hengerleie: VerdiFelt<String>?,
    val bom: VerdiFelt<String>?,
    val parkering: VerdiFelt<String>?,
    val ferje: VerdiFelt<String>?,
    val annet: VerdiFelt<String>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Utgifter ved egen flytting",
        )
}

data class Flyttebyrå(
    val tilbud: List<FlyttebyråTilbud>,
    val valgtTilbud: EnumFelt<ValgtTilbud>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Flyttebyrå",
        )
}

data class FlyttebyråTilbud(
    val navn: VerdiFelt<String>?,
    val beløp: VerdiFelt<String>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Tilbud fra flyttebyrå",
        )
}
