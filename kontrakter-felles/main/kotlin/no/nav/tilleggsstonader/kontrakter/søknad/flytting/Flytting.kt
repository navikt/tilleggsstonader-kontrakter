package no.nav.tilleggsstonader.kontrakter.søknad.flytting

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.VerdiFelt
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.Adresse
import java.math.BigDecimal

enum class Flytteårsak {
    NY_JOBB,
    AKTIVITET,
}

enum class Flyttemåte {
    FLYTTER_SELV,
    FLYTTEBYRÅ,
}

data class FlyttingDetaljer(
    val årsak: EnumFelt<Flytteårsak>,
    val flyttedato: VerdiFelt<String>,
    val adresse: Adresse,
    val flyttemåte: EnumFelt<Flyttemåte>,
    val utgifterDekketPåAnnenMåte: EnumFelt<JaNei>,
    val utgifterEgenFlytting: UtgifterEgenFlytting?,
    val flyttebyrå: Flyttebyrå?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Detaljer om flyttingen",
        )
}

data class UtgifterEgenFlytting(
    val avstand: VerdiFelt<BigDecimal>,
    val hengerleie: VerdiFelt<BigDecimal>?,
    val bom: VerdiFelt<BigDecimal>?,
    val parkering: VerdiFelt<BigDecimal>?,
    val ferje: VerdiFelt<BigDecimal>?,
    val annet: VerdiFelt<BigDecimal>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Utgifter ved egen flytting",
        )
}

data class Flyttebyrå(
    val tilbud: List<FlyttebyråTilbud>,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Flyttebyrå",
        )

    init {
        require(tilbud.size == 2) { "Det skal alltid være to tilbud tilgjengelig" }
    }
}

data class FlyttebyråTilbud(
    val navn: VerdiFelt<String>,
    val beløp: VerdiFelt<BigDecimal>,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Tilbud fra flyttebyrå",
        )
}
