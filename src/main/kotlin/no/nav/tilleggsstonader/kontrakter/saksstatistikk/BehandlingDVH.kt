package no.nav.tilleggsstonader.kontrakter.saksstatistikk

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.ZonedDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BehandlingDVH(
    val behandlingId: Long, // Fagsystemets eksterne behandlings-ID
    val relatertBehandlingId: Long? = null, // Hvis behandlingen har oppsått med bakgrunn i en annen, skal den foregående behandlingen refereres til her
    val sakId: Long, // Saksnummer som følger behandlingen for NAV globalt
    val saksnummer: Long, // Saksnummer som følger behandlingen for NAV globalt
    val personIdent: String, // PersonIdent tilknyttet søker eller hovedaktør for ytelsen
    val mottattTid: ZonedDateTime? = null, // Tidspunktet da behandlingen oppstår (eks søknadstidspunkt, inntektsmelding, etc). Det er ønskelig å måle brukers opplevde ventetid. Ved elektronisk kontakt regner vi med at denne er lik registrertTid
    val registrertTid: ZonedDateTime, // Tidspunkt da behandlingen først oppstod eller ble registrert i fagsystemet
    val ferdigBehandletTid: ZonedDateTime? = null, // Tidspunkt når behandling ble avsluttet, enten avbrutt, henlagt, vedtak innvilget/avslått, etc
    val vedtakTid: ZonedDateTime? = null, // Tidspunktet for når vedtaket ble fattet - hvis saken ble vedtatt
    val endretTid: ZonedDateTime, // Tidspunkt for siste endring på behandlingen. Ved første melding vil denne være lik registrertTid
    val tekniskTid: ZonedDateTime, // Tidspunktet da fagsystemet legger hendelsen på grensesnittet/topicen
    val sakYtelse: String, // Kode som angir hvilken ytelse/stønad behandlingen gjelder
    val sakUtland: String? = null, // Nasjonal/Utland - Kode som angir hvor vidt saken er for utland eller nasjonal å anses. Se begrepskatalogen: https://jira.adeo.no/browse/BEGREP-1611#
    val behandlingType: String, // Kode som angir hvilken type behandling det er snakk om - typisk: søknad, revurdering, tilbakekreving, klage, etc.
    val behandlingStatus: String, // Kode som angir hvilken status behandlingen har - typisk: opprettet, under behandling, avsluttet, etc
    val behandlingResultat: String? = null, // Kode som angir resultatet på behandling - typisk: avbrutt, innvilget, delvis innvilget, henlagt av tekniske hensyn, etc
    val resultatBegrunnelse: String? = null, // Kode som angir en begrunnelse til resultat - typisk: vilkårsprøvingen feilet, dublett, teknisk avvik, etc
    val behandlingMetode: String? = null, // Kode som angir om saken er behandlet manuelt eller automatisk (hvis fagsystemet opererer med en slik verdi)
    val opprettetAv: String, // [Feltet er geo-lokaliserende og skal oppgis som -5 hvis noen personer tilknyttet behandlingen er kode 6] Saksbehandler-ID som opprettet behandlingen. Hvis det er en servicebruker så sende denne
    val saksbehandler: String? = null, // [Feltet er geo-lokaliserende og skal oppgis som -5 hvis noen personer tilknyttet behandlingen er kode 6] Saksbehandler-ID som sist var involvert i behandlingen
    val ansvarligBeslutter: String? = null, // [Feltet er geo-lokaliserende og skal oppgis som -5 hvis noen personer tilknyttet behandlingen er kode 6, men kun om det skulle hatt verdi] Ved krav om totrinnskontroll skal dette feltet innholde ansvarlig beslutter sin ID
    val ansvarligEnhet: String, // [Feltet er geo-lokaliserende og skal oppgis som -5 hvis noen personer tilknyttet behandlingen er kode 6] Hvilken org enhet som nå har ansvar for saken. Dette kan være samme som opprettetEnhet. Avslåtte klager i vedtaksinstans skal ha riktig KA-enhet her
    val totrinnsbehandling: Boolean, // Hvis det er utført totrinnskontroll skal denne være true
    val datoForsteUtbetaling: ZonedDateTime? = null, // Hvis systemet eller bruker har et forhold til når ytelsen normalt skal utbetales (planlagt uttak, ønsket oppstart etc)
    val venteAarsak: String? = null, // Kode som angir årsak til venting/utsettelse av saksbehandlings prosesser - typisk: venter på utland, venter på inntektsmelding etc.
    val behandlingÅrsak: String? = null, // Årsak til opprettet behandling - typisk klage, nye opplysninger, sanksjon, søknad, migrering, g-omregning, korrigering og papirsøknad
    val kravMottatt: ZonedDateTime? = null, // Dato for når krav eller informasjon om at man må opprette revurdering ble mottatt
    val revurderingOpplysningskilde: String? = null, // Opplysningskilde til hvorfor det må gjøres en revurdering, eks MODIA
    val revurderingÅrsak: String? = null, // Årsak til revurdering, eks ENDRING_I_INNTEKT
    val avslagAarsak: String? = null, // Eks: VILKÅR_IKKE_OPPFYLT, BARN_OVER_ÅTTE_ÅR, STØNADSTID_OPPBRUKT, MANGLENDE_OPPLYSNINGER, MINDRE_INNTEKTSENDRINGER
    val versjon: String? = null, // "Kode som hvilken versjonen av koden dataene er generert med bakgrunn på. Kan godt være relatert til Git repoet
    val avsender: String? = null, // Angir fagsystemets eget navn
)
