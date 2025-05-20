package no.nav.tilleggsstonader.kontrakter.journalpost

import no.nav.tilleggsstonader.kontrakter.felles.BrukerIdType
import java.time.LocalDateTime

data class Journalpost(
    val journalpostId: String,
    val journalposttype: Journalposttype,
    val journalstatus: Journalstatus,
    val tema: String? = null,
    val behandlingstema: String? = null,
    val tittel: String? = null,
    val sak: Sak? = null,
    val bruker: Bruker? = null,
    val avsenderMottaker: AvsenderMottaker? = null,
    val journalforendeEnhet: String? = null,
    val kanal: String? = null,
    val dokumenter: List<DokumentInfo>? = null,
    val relevanteDatoer: List<RelevantDato>? = null,
    val eksternReferanseId: String? = null,
    val utsendingsinfo: Utsendingsinfo? = null,
) {
    val datoMottatt = relevanteDatoer?.firstOrNull { it.datotype == "DATO_REGISTRERT" }?.dato
}

data class RelevantDato(
    val dato: LocalDateTime,
    val datotype: String,
)

data class Bruker(
    val id: String,
    val type: BrukerIdType,
)

enum class Journalposttype {
    I,
    U,
    N,
}

enum class Journalstatus {
    MOTTATT,
    JOURNALFOERT,
    FERDIGSTILT,
    EKSPEDERT,
    UNDER_ARBEID,
    FEILREGISTRERT,
    UTGAAR,
    AVBRUTT,
    UKJENT_BRUKER,
    RESERVERT,
    OPPLASTING_DOKUMENT,
    UKJENT,
}
