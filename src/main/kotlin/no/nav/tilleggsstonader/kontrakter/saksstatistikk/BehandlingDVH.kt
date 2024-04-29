package no.nav.tilleggsstonader.kontrakter.saksstatistikk

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate
import java.time.ZonedDateTime

// Følgende felter er med i felleskontrakten til Team Sak. Vi bør etterstrebe å sende så mange av dem som mulig.
@JsonInclude(JsonInclude.Include.NON_NULL)
data class BehandlingDVH(
    val behandlingId: String, // Fagsystemets eksterne behandlings-ID
    val behandlingUuid: String, // Behandlingens UUID - for oss trolig lik behandlingId
    val relatertBehandlingId: String? = null, // Hvis behandlingen har oppsått med bakgrunn i en annen, skal den foregående behandlingen refereres til her
    val sakId: String, // Saksnummer som følger behandlingen for NAV globalt
    val saksnummer: String, // Saksnummer som følger behandlingen for NAV globalt
    val aktorId: String, // PersonIdent tilknyttet søker eller hovedaktør for ytelsen
    val mottattTid: ZonedDateTime? = null, // Tidspunktet da behandlingen oppstår (eks søknadstidspunkt, inntektsmelding, etc). Det er ønskelig å måle brukers opplevde ventetid. Ved elektronisk kontakt regner vi med at denne er lik registrertTid
    val registrertTid: ZonedDateTime? = null, // Tidspunkt da behandlingen først oppstod eller ble registrert i fagsystemet
    val ferdigBehandletTid: ZonedDateTime? = null, // Tidspunkt når behandling ble avsluttet, enten avbrutt, henlagt, vedtak innvilget/avslått, etc
    val vedtakTid: ZonedDateTime? = null, // Tidspunktet for når vedtaket ble fattet - hvis saken ble vedtatt
    val utbetaltTid: LocalDate? = null, // Tidspunkt for første utbetaling av ytelse. Tilsvarer datoForsteUtbetaling i EF sin implemenetasjon
    val endretTid: ZonedDateTime? = null, // Tidspunkt for siste endring på behandlingen. Ved første melding vil denne være lik registrertTid
    val forventetOppstartTid: LocalDate? = null, // Hvis systemet eller bruker har et forhold til når ytelsen normalt skal utbetales (planlagt uttak, ønsket oppstart etc).
    val tekniskTid: ZonedDateTime, // Tidspunktet da fagsystemet legger hendelsen på grensesnittet/topicen
    val papirSøknad: String? = null, // Flagg som angir om opprinnelsen til søknaden er fra et papirskjema.
    val sakYtelse: String, // Kode som angir hvilken ytelse/stønad behandlingen gjelder
    val sakUtland: String? = null, // Nasjonal/Utland - Kode som angir hvor vidt saken er for utland eller nasjonal å anses. Se begrepskatalogen: https://jira.adeo.no/browse/BEGREP-1611#
    val behandlingType: String, // Kode som angir hvilken type behandling det er snakk om - typisk: søknad, revurdering, tilbakekreving, klage, etc.
    val behandlingStatus: String, // Kode som angir hvilken status behandlingen har - typisk: opprettet, under behandling, avsluttet, etc
    val behandlingResultat: String? = null, // Kode som angir resultatet på behandling - typisk: avbrutt, innvilget, delvis innvilget, henlagt av tekniske hensyn, etc
    val resultatBegrunnelse: String? = null, // Kode som angir en begrunnelse til resultat - typisk: vilkårsprøvingen feilet, dublett, teknisk avvik, etc
    val behandlingMetode: String? = null, // Kode som angir om saken er behandlet manuelt eller automatisk (hvis fagsystemet opererer med en slik verdi).
    val opprettetAv: String, // [Feltet er geo-lokaliserende og skal oppgis som -5 hvis noen personer tilknyttet behandlingen er kode 6] Saksbehandler-ID som opprettet behandlingen. Hvis det er en servicebruker så sende denne
    val saksbehandler: String? = null, // [Feltet er geo-lokaliserende og skal oppgis som -5 hvis noen personer tilknyttet behandlingen er kode 6] Saksbehandler-ID som sist var involvert i behandlingen
    val ansvarligBeslutter: String? = null, // [Feltet er geo-lokaliserende og skal oppgis som -5 hvis noen personer tilknyttet behandlingen er kode 6, men kun om det skulle hatt verdi] Ved krav om totrinnskontroll skal dette feltet innholde ansvarlig beslutter sin ID
    val ansvarligEnhet: String, // [Feltet er geo-lokaliserende og skal oppgis som -5 hvis noen personer tilknyttet behandlingen er kode 6] Hvilken org enhet som nå har ansvar for saken. Dette kan være samme som opprettetEnhet. Avslåtte klager i vedtaksinstans skal ha riktig KA-enhet her
    val totrinnsbehandling: TotrinnsbehandlingStatusDvh = TotrinnsbehandlingStatusDvh.IKKE_GJENNOMFØRT, // Status på totrinnskontrollen. GODKJENT, UNDERKJENT eller IKKE_GJENNOMFØRT,
    val datoForsteUtbetaling: ZonedDateTime? = null, // Hvis systemet eller bruker har et forhold til når ytelsen normalt skal utbetales (planlagt uttak, ønsket oppstart etc)
    val avsender: String? = null, // Angir fagsystemets eget navn

    // TODO: Implementer dette feltet sammen med team Sak. Det er litt usikkerhet rundt hvordan vi skal implementere nøstede vilkårsvurderinger.
    val vilkårsprøving: List<VilkårsprøvingDVH>, // Liste med aktuelle vilkår, og resultat av vilkårsprøving. Brukes også for å utlede årsak til avslag. Innhold i liste: VilkårID, Beskrivelse/navn, resultat

    // Felter som vi sender hvis vi kan, men som vi fjerner hvis vi finner ut at de ikke er relevante (eller alltid null) for oss.
    val venteAarsak: String? = null, // Kode som angir årsak til venting/utsettelse av saksbehandlings prosesser - typisk: venter på utland, venter på inntektsmelding etc.
    val behandlingBegrunnelse: String? = null, // EF sender dette
    val revurderingOpplysningskilde: String? = null, // Opplysningskilde til hvorfor det må gjøres en revurdering, eks MODIA
    val revurderingÅrsak: String? = null, // Årsak til revurdering, eks ENDRING_I_INNTEKT
    val kravMottatt: ZonedDateTime? = null, // Dato for når krav eller informasjon om at man må opprette revurdering ble mottatt
    val behandlingÅrsak: String? = null, // Årsak til opprettet behandling - typisk klage, nye opplysninger, sanksjon, søknad, migrering, g-omregning, korrigering og papirsøknad
    val versjon: String? = null, // "Kode som hvilken versjonen av koden dataene er generert med bakgrunn på. Kan godt være relatert til Git repoet. Alltid null hos EF. Send hvis ikke for mye stress å implementere.
)

data class VilkårsprøvingDVH(
    val vilkårId: String,
    val beskrivelse: String,
    val resultat: String,
)

enum class TotrinnsbehandlingStatusDvh {
    GODKJENT,
    UNDERKJENT,
    IKKE_GJENNOMFØRT,
}
