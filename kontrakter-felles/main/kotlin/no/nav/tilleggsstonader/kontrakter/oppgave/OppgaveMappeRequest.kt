package no.nav.tilleggsstonader.kontrakter.oppgave

data class FinnMappeRequest(
    val tema: List<String>? = null,
    val enhetsnr: String,
    val opprettetFom: String? = null,
    val limit: Int = 1000,
)

data class FinnMappeResponseDto(
    val antallTreffTotalt: Int,
    val mapper: List<MappeDto>,
)

data class MappeDto(
    val id: Long,
    val navn: String,
    val enhetsnr: String,
    val tema: String? = null,
)
