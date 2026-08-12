package no.nav.tilleggsstonader.kontrakter.oppgave

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import no.nav.tilleggsstonader.kontrakter.felles.Tema
import java.time.LocalDate
import java.util.Optional

data class OppgaveResponse(
    val oppgaveId: Long,
)

data class OppdatertOppgaveResponse(
    val oppgaveId: Long,
    val versjon: Int,
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Oppgave(
    val id: Long,
    val versjon: Int,
    val tildeltEnhetsnr: String? = null,
    val tema: Tema? = null,
    val oppgavetype: String? = null,
    val prioritet: OppgavePrioritet? = null,
    val status: StatusEnum? = null,
    val aktivDato: LocalDate? = null,
    @Deprecated("Skal ikke brukes, foretrekk bruker og OppgaveBruker")
    val identer: List<OppgaveIdentV2>? = null,
    val bruker: OppgaveBruker? = null,
    val endretAvEnhetsnr: String? = null,
    val opprettetAvEnhetsnr: String? = null,
    val journalpostId: String? = null,
    val journalpostkilde: String? = null,
    val behandlesAvApplikasjon: String? = null,
    val saksreferanse: String? = null,
    val bnr: String? = null,
    val samhandlernr: String? = null,
    val aktoerId: String? = null,
    val personident: String? = null,
    val orgnr: String? = null,
    val tilordnetRessurs: String? = null,
    val beskrivelse: String? = null,
    val temagruppe: String? = null,
    val behandlingstema: String? = null,
    val behandlingstype: String? = null,
    val mappeId: Optional<Long>? = null,
    val fristFerdigstillelse: LocalDate? = null,
    val opprettetTidspunkt: String? = null,
    val opprettetAv: String? = null,
    val endretAv: String? = null,
    val ferdigstiltTidspunkt: String? = null,
    val endretTidspunkt: String? = null,
    private var metadata: MutableMap<String, String>? = null,
)

@Deprecated("Skal ikke brukes, foretrekk bruker og OppgaveBruker")
data class OppgaveIdentV2(
    val ident: String?,
    val gruppe: IdentGruppe?,
)

@Deprecated("Skal ikke brukes, foretrekk bruker og OppgaveBruker")
enum class IdentGruppe {
    AKTOERID,
    FOLKEREGISTERIDENT,
    NPID,
    ORGNR,
    SAMHANDLERNR,
}

enum class StatusEnum {
    OPPRETTET,
    AAPNET,
    UNDER_BEHANDLING,
    FERDIGSTILT,
    FEILREGISTRERT,
}

enum class OppgaveBrukerType {
    PERSON,
    ARBEIDSGIVER,
    SAMHANDLER,
}

data class OppgaveBruker(
    val ident: String?,
    val type: OppgaveBrukerType?,
)

// Disse burde oppdateres til de som er gjeldende for våre temaer, finnes på swagger på https://oppgave.dev.intern.nav.no/
// ev bestille de som mangler
enum class Oppgavetype(
    val value: String,
) {
    BehandleSak("BEH_SAK"),
    BehandleHenvendelse("BEH_HENV"),
    BehandleAvvistAdresse("BEH_AVV_ADR"),
    Journalføring("JFR"),
    GodkjenneVedtak("GOD_VED"),
    BehandleUnderkjentVedtak("BEH_UND_VED"),
    Fordeling("FDR"),
    BehandleKjøreliste("BEH_KJORELISTE"),
    BehandleReturpost("RETUR"),
    BehandleSED("BEH_SED"),
    FordelingSED("FDR_SED"),
    Fremlegg("FREM"),
    Generell("GEN"),
    InnhentDokumentasjon("INNH_DOK"),
    JournalføringUtgående("JFR_UT"),
    KontaktBruker("KONT_BRUK"),
    KontrollerUtgåendeSkannetDokument("KON_UTG_SCA_DOK"),
    SvarIkkeMottatt("SVAR_IK_MOT"),
    VurderDokument("VUR"),
    VurderHenvendelse("VURD_HENV"),
    VurderKonsekvensForYtelse("VUR_KONS_YTE"),
    VurderLivshendelse("VURD_LIVS"),
    VurderSvar("VUR_SVAR"),
}

enum class Behandlingstype(
    val value: String,
) {
    Utland("ae0106"),
    NASJONAL("ae0118"),
    EØS("ae0120"),
    Tilbakekreving("ae0161"),
    Klage("ae0058"),
}

enum class OppgavePrioritet {
    HOY,
    NORM,
    LAV,
    KRITISK,
}
