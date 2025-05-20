package no.nav.tilleggsstonader.kontrakter.dokarkiv

data class ArkiverDokumentResponse(
    val journalpostId: String,
    val ferdigstilt: Boolean,
    val dokumenter: List<DokumentInfo>? = null,
) {
    init {
        require(journalpostId.isNotBlank()) { "journalpostId kan ikke være empty" }
    }
}
