package no.nav.tilleggsstonader.kontrakter.dokarkiv

class Dokument(
    val dokument: ByteArray,
    val filtype: Filtype,
    val filnavn: String? = null,
    val tittel: String? = null,
    val dokumenttype: Dokumenttype,
)

enum class Filtype {
    PDFA,
    JSON,
}
