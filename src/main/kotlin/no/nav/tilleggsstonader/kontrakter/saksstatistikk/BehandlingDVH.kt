package no.nav.tilleggsstonader.kontrakter.saksstatistikk

import com.fasterxml.jackson.annotation.JsonInclude
import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import java.time.LocalDate
import java.time.LocalDateTime

interface BehandlingsstatistikkDvh {
    val behandlingId: String // Fagsystemets eksterne behandlings-ID
    val behandlingUuid: String // Behandlingens UUID - for oss trolig lik behandlingId
    val saksnummer: String // Saksnummer som følger behandlingen
    val sakId: String // UUID til saken som følger behandlingen
    val aktorId: String // PersonIdent tilknyttet søker eller hovedaktør for ytelsen
    val mottattTid: LocalDateTime // Tidspunktet da behandlingen oppstår (eks søknadstidspunkt, inntektsmelding, etc). Det er ønskelig å måle brukers opplevde ventetid. Ved elektronisk kontakt regner vi med at denne er lik registrertTid
    val registrertTid: LocalDateTime // Tidspunkt da behandlingen først oppstod eller ble registrert i fagsystemet
    val ferdigBehandletTid: LocalDateTime? // Tidspunkt når behandling ble avsluttet, enten avbrutt, henlagt, vedtak innvilget/avslått, etc
    val endretTid: LocalDateTime // Tidspunkt for siste endring på behandlingen. Ved første melding vil denne være lik registrertTid
    val tekniskTid: LocalDateTime // Tidspunktet da fagsystemet legger hendelsen på grensesnittet/topicen
    val sakYtelse: String // Kode som angir hvilken ytelse/stønad behandlingen gjelder
    val sakUtland: String? // Nasjonal/Utland - Kode som angir hvor vidt saken er for utland eller nasjonal å anses. Se begrepskatalogen: https://jira.adeo.no/browse/BEGREP-1611#
    val behandlingType: String // Kode som angir hvilken type behandling det er snakk om - typisk: søknad, revurdering, tilbakekreving, klage, etc.
    val behandlingStatus: String // Kode som angir hvilken status behandlingen har - typisk: opprettet, under behandling, avsluttet, etc
    val behandlingMetode: String? // Kode som angir om saken er behandlet manuelt eller automatisk (hvis fagsystemet opererer med en slik verdi).
    val opprettetAv: String // [Feltet er geo-lokaliserende og skal oppgis som -5 hvis noen personer tilknyttet behandlingen er kode 6] Saksbehandler-ID som opprettet behandlingen. Hvis det er en servicebruker så sende denne
    val kravMottatt: LocalDate? // Dato for når krav eller informasjon om at man må opprette revurdering ble mottatt
    val saksbehandler: String // [Feltet er geo-lokaliserende og skal oppgis som -5 hvis noen personer tilknyttet behandlingen er kode 6] Saksbehandler-ID som sist var involvert i behandlingen
    val ansvarligEnhet: String // [Feltet er geo-lokaliserende og skal oppgis saom -5 hvis noen personer tilknyttet behandlingen er kode 6] Hvilken org enhet som nå har ansvar for saken. Dette kan være samme som opprettetEnhet. Avslåtte klager i vedtaksinstans skal ha riktig KA-enhet her
    val behandlingResultat: String? // Kode som angir resultatet på behandling - typisk: avbrutt, innvilget, delvis innvilget, henlagt av tekniske hensyn, etc
    val resultatBegrunnelse: String? // Kode som angir en begrunnelse til resultat - typisk: vilkårsprøvingen feilet, dublett, teknisk avvik, etc
    val avsender: String // Angir fagsystemets eget navn
    val versjon: String? // "Kode som hvilken versjonen av koden dataene er generert med bakgrunn på. Kan godt være relatert til Git repoet. Alltid null hos EF. Send hvis ikke for mye stress å implementere.
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BehandlingKlageDvh(
    override val behandlingId: String,
    override val behandlingUuid: String,
    override val saksnummer: String,
    override val sakId: String,
    override val aktorId: String,
    override val mottattTid: LocalDateTime,
    override val registrertTid: LocalDateTime,
    override val ferdigBehandletTid: LocalDateTime?,
    override val endretTid: LocalDateTime,
    override val tekniskTid: LocalDateTime,
    override val kravMottatt: LocalDate?,
    override val sakYtelse: String,
    override val sakUtland: String?,
    override val behandlingType: String,
    override val behandlingStatus: String,
    override val behandlingMetode: String?,
    override val opprettetAv: String,
    override val saksbehandler: String,
    override val ansvarligEnhet: String,
    override val behandlingResultat: String? = null,
    override val resultatBegrunnelse: String? = null,
    override val avsender: String,
    override val versjon: String?,

    val relatertEksternBehandlingId: String? = null, // Fagsystemet sin eksterne behandlingId, hvis klagen er koblet til en behandling
    val relatertFagsystemType: String? = null, // Påklaget behandling sin fagsystemtype, eks ORDINÆR eller TILBAKEKREVING
) : BehandlingsstatistikkDvh

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BehandlingDVH(
    override val behandlingId: String,
    override val behandlingUuid: String,
    override val saksnummer: String,
    override val sakId: String,
    override val aktorId: String,
    override val mottattTid: LocalDateTime,
    override val registrertTid: LocalDateTime,
    override val ferdigBehandletTid: LocalDateTime?,
    override val endretTid: LocalDateTime,
    override val tekniskTid: LocalDateTime,
    override val sakYtelse: String,
    override val sakUtland: String?,
    override val behandlingType: String,
    override val behandlingStatus: String,
    override val behandlingMetode: String?,
    override val kravMottatt: LocalDate?,
    override val opprettetAv: String,
    override val saksbehandler: String,
    override val ansvarligEnhet: String,
    override val behandlingResultat: String? = null,
    override val resultatBegrunnelse: String? = null,
    override val avsender: String,
    override val versjon: String?,

    val relatertBehandlingId: String? = null, // Hvis behandlingen har oppsått med bakgrunn i en annen, skal den foregående behandlingen refereres til her
    val vedtakTid: LocalDateTime? = null, // Tidspunktet for når vedtaket ble fattet - hvis saken ble vedtatt
    val utbetaltTid: LocalDate? = null, // Tidspunkt for første utbetaling av ytelse. Tilsvarer datoForsteUtbetaling i EF sin implemenetasjon
    val forventetOppstartTid: LocalDate? = null, // Hvis systemet eller bruker har et forhold til når ytelsen normalt skal utbetales (planlagt uttak, ønsket oppstart etc).
    val papirSøknad: String? = null, // Flagg som angir om opprinnelsen til søknaden er fra et papirskjema.
    val ansvarligBeslutter: String? = null, // [Feltet er geo-lokaliserende og skal oppgis som -5 hvis noen personer tilknyttet behandlingen er kode 6, men kun om det skulle hatt verdi] Ved krav om totrinnskontroll skal dette feltet innholde ansvarlig beslutter sin ID
    val totrinnsbehandling: Boolean, // Flagg som viser om totrinnsbehandling har blitt gjennomført

    // TODO: Implementer dette feltet sammen med team Sak. Det er litt usikkerhet rundt hvordan vi skal implementere nøstede vilkårsvurderinger.
    val vilkårsprøving: List<VilkårsprøvingDVH>, // Liste med aktuelle vilkår, og resultat av vilkårsprøving. Brukes også for å utlede årsak til avslag. Innhold i liste: VilkårID, Beskrivelse/navn, resultat

    val venteAarsak: String? = null, // Kode som angir årsak til venting/utsettelse av saksbehandlings prosesser - typisk: venter på utland, venter på inntektsmelding etc.
    val behandlingBegrunnelse: String? = null, // EF sender dette
    val revurderingOpplysningskilde: String? = null, // Opplysningskilde til hvorfor det må gjøres en revurdering, eks MODIA
    val revurderingÅrsak: String? = null, // Årsak til revurdering, eks ENDRING_I_INNTEKT
    val behandlingÅrsak: String? = null, // Årsak til opprettet behandling - typisk klage, nye opplysninger, sanksjon, søknad, migrering, g-omregning, korrigering og papirsøknad
) : BehandlingsstatistikkDvh

data class VilkårsprøvingDVH(
    val vilkårId: String,
    val beskrivelse: String,
    val resultat: String,
)

/*
 * Saksbehandlingsstatistikken trenger informasjon om behandlingen gjelder "Mobilitetsfremmende stønad", "Tilleggsstønad" eller "Tilleggsstønad arbeidssøkere".
 */
enum class SakYterlseDvh {
    TILLEGG_BARNETILSYN; // For pass av barn er kun "Tilleggsstønad" relevant

    companion object {
        fun fraStøndstype(stønadstype: Stønadstype) = when (stønadstype) {
            Stønadstype.BARNETILSYN -> TILLEGG_BARNETILSYN
        }
    }
}