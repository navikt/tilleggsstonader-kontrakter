package no.nav.tilleggsstonader.kontrakter.dokarkiv

/**
 * @param eksternReferanseId for idempotens
 */
data class ArkiverDokumentRequest(
    val fnr: String,
    val forsøkFerdigstill: Boolean,
    val hoveddokumentvarianter: List<Dokument>,
    val vedleggsdokumenter: List<Dokument> = emptyList(),
    val fagsakId: String? = null,
    // Skal ikke settes for innkommende hvis Ruting gjøres av BRUT001
    val journalførendeEnhet: String? = null,
    val eksternReferanseId: String,
    val avsenderMottaker: AvsenderMottaker? = null,
) {
    init {
        require(fnr.isNotBlank()) { "fnr kan ikke være empty" }
        require(hoveddokumentvarianter.isNotEmpty()) { "hoveddokumentvarianter kan ikke være empty" }
    }
}
