package no.nav.tilleggsstonader.kontrakter.journalpost

data class DokumentInfo(
    val dokumentInfoId: String,
    val tittel: String? = null,
    val brevkode: String? = null,
    val dokumentstatus: Dokumentstatus? = null,
    val dokumentvarianter: List<Dokumentvariant>? = null,
    val logiskeVedlegg: List<LogiskVedlegg>? = null,
)

enum class Dokumentstatus {
    FERDIGSTILT,
    AVBRUTT,
    UNDER_REDIGERING,
    KASSERT,
}

data class Dokumentvariant(val variantformat: Dokumentvariantformat, val filnavn: String? = null)

enum class Dokumentvariantformat {
    ORIGINAL,
    ARKIV,
    FULLVERSJON,
    PRODUKSJON,
    PRODUKSJON_DLF,
    SLADDET,
}
