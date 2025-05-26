package no.nav.tilleggsstonader.kontrakter.kodeverk

import java.time.LocalDate

data class KodeverkDto(
    val betydninger: Map<String, List<BetydningDto>>,
)

data class BetydningDto(
    val gyldigFra: LocalDate,
    val gyldigTil: LocalDate,
    val beskrivelser: Map<String, BeskrivelseDto>,
)

data class BeskrivelseDto(
    val term: String,
    val tekst: String,
)

enum class KodeverkSpråk(
    val kode: String,
) {
    BOKMÅL("nb"),
}

private fun LocalDate.mellom(
    fra: LocalDate,
    til: LocalDate,
) = this.isEqual(fra) || this.isEqual(til) || (this.isAfter(fra) && this.isBefore(til))

/**
 * @param sisteGjeldende henter siste gjeldende verdi for en gitt kode hvis den mangler for gitt dato
 */
fun KodeverkDto.hentGjeldende(
    kode: String,
    gjeldendeDato: LocalDate = LocalDate.now(),
    språk: KodeverkSpråk = KodeverkSpråk.BOKMÅL,
    sisteGjeldende: Boolean = false,
): String? {
    val betydningForKode = betydninger[kode] ?: return null
    var betydning = betydningForKode.firstOrNull { gjeldendeDato.mellom(it.gyldigFra, it.gyldigTil) }
    if (betydning == null && sisteGjeldende) {
        betydning = betydningForKode.maxByOrNull { it.gyldigFra }
    }
    return betydning?.beskrivelser?.get(språk.kode)?.term
}

/**
 * Mapper [KodeverkDto] til en Map<term, tekst> der term er koden
 * [term] er et navnet på feltet fra kodeverk
 */
fun KodeverkDto.mapTerm(språk: KodeverkSpråk): Map<String, String> =
    betydninger.mapValues {
        if (it.value.isEmpty()) {
            ""
        } else if (it.value.size != 1) {
            this.hentGjeldende(it.key, språk = språk) ?: ""
        } else {
            it.value
                .singleOrNull()
                ?.beskrivelser
                ?.get(språk.kode)
                ?.term ?: ""
        }
    }
