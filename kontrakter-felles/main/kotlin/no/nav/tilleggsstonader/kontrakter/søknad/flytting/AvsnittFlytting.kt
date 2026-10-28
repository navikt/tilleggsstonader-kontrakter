package no.nav.tilleggsstonader.kontrakter.søknad.flytting

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.Avsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.EnumFelt
import no.nav.tilleggsstonader.kontrakter.søknad.JaNei
import no.nav.tilleggsstonader.kontrakter.søknad.SelectFelt
import no.nav.tilleggsstonader.kontrakter.søknad.VerdiFelt
import java.math.BigDecimal

enum class Flytteårsak {
    NY_JOBB,
    AKTIVITET,
}

enum class Flyttemåte {
    FLYTTER_SELV,
    FLYTTEBYRÅ,
}

data class FlyttingDetaljerAvsnitt(
    val årsak: EnumFelt<Flytteårsak>,
    val flyttedato: VerdiFelt<String>,
    val adresse: TilAdresseAvsnitt,
    val skalFlytteFraFolkeregistrertAdresse: EnumFelt<JaNei>,
    val adresseDetSkalFlyttesFra: FraAdresseAvsnitt,
    val flyttemåte: EnumFelt<Flyttemåte>,
    val utgifterDekketPåAnnenMåte: EnumFelt<JaNei>,
    val utgifterEgenFlytting: UtgifterEgenFlyttingAvsnitt?,
    val flyttebyrå: FlyttebyråAvsnitt?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Detaljer om flyttingen",
        )
}

data class FraAdresseAvsnitt(
    val land: SelectFelt<String>?,
    val gateadresse: VerdiFelt<String>?,
    val postnummer: VerdiFelt<String>?,
    val poststed: VerdiFelt<String>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Adresse du flytter fra",
        )
}

data class TilAdresseAvsnitt(
    val land: SelectFelt<String>?,
    val gateadresse: VerdiFelt<String>?,
    val postnummer: VerdiFelt<String>?,
    val poststed: VerdiFelt<String>?,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Adresse du flytter til",
        )
}

data class UtgifterEgenFlyttingAvsnitt(
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

data class FlyttebyråAvsnitt(
    val tilbud: List<FlyttebyråTilbud>,
) : Avsnitt {
    override fun språkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Tilbud fra flyttebyrå",
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
            Språkkode.NB to "Flyttebyrå",
        )
}
