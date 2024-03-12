package no.nav.tilleggsstonader.kontrakter.stonadsstatistikk

import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import java.time.LocalDate
import java.time.ZonedDateTime

data class VedtakPassAvBarn(
    val fagsakId: Long, // Ekstern fagsakId
    val behandlingId: Long, // Ekstern behandlingId
    val relatertBehandlingId: Long? = null, // Ekstern behandlingId på relatert behandling
    val adressebeskyttelse: Adressebeskyttelse? = null,
    val tidspunktVedtak: ZonedDateTime? = null,
    val vilkårsvurderinger: List<VilkårsvurderingDto>,
    val person: Person,
    val barn: List<Barn>,
    val behandlingType: String,
    val behandlingÅrsak: BehandlingÅrsak,
    val vedtak: Vedtak? = null,
    val vedtaksperioder: List<VedtaksperiodeBarnetilsynDto>, //TODO må tilpasse hvordan periode er definert internt
    val utbetalinger: List<Utbetaling>,
    val funksjonellId: Long? = null,
    val stønadstype: Stønadstype,
    val kravMottatt: ZonedDateTime? = null,
    val årsakRevurdering: ÅrsakRevurdering? = null, //TODO tomt objekt fra til revurdering er på plass
    val avslagÅrsak: String? = null
)

enum class Vedtak {
    INNVILGET,
    OPPHØRT,
    AVSLÅTT
}

enum class Adressebeskyttelse {
    STRENGT_FORTROLIG,
    STRENGT_FORTROLIG_UTLAND,
    FORTROLIG,
    UGRADERT
}
enum class BehandlingÅrsak {
    SØKNAD,
    NYE_OPPLYSNINGER,
    KLAGE,
    MIGRERING,
    KORRIGERING_UTEN_BREV,
    IVERKSETTE_KA_VEDTAK,
    SATSENDRING,
    MANUELT_OPPRETTET
}

data class Person(val personIdent: String? = null)

data class Barn(val personIdent: String? = null, val fødselsdato: LocalDate? = null)

data class Utbetaling(
    val beløp: Int,
    val fraOgMed: ZonedDateTime,
    val tilOgMed: ZonedDateTime,
    val utbetalingsdetalj: Utbetalingsdetalj
)
data class Utbetalingsdetalj(
    val gjelderPerson: Person,
    val klassekode: String, // Identifiserer detaljert stønadstype i oppdragsystemet: " "
) // Identifiserer utbetalinga i oppdragssystemet

data class VilkårsvurderingDto(
    val vilkår: Vilkår,
    val resultat: Vilkårsresultat
)
enum class Vilkårsresultat {
    OPPFYLT,
    AUTOMATISK_OPPFYLT,
    IKKE_OPPFYLT,
    IKKE_AKTUELL,
    IKKE_TATT_STILLING_TIL,
    SKAL_IKKE_VURDERES;
}
//TODO samstemme med korleis intern persiode er definert for fornutfige data
data class VedtaksperiodeBarnetilsynDto(
    val fraOgMed: LocalDate,
    val tilOgMed: LocalDate,
    val utgifter: Int,
    val antallBarn: Int
)


enum class Vilkår {
    FORUTGÅENDE_MEDLEMSKAP,
    LOVLIG_OPPHOLD,
    MOR_ELLER_FAR,
    SIVILSTAND,
    SAMLIV,
    ALENEOMSORG,
    NYTT_BARN_SAMME_PARTNER,
    SAGT_OPP_ELLER_REDUSERT,
    AKTIVITET,
    AKTIVITET_ARBEID,
    TIDLIGERE_VEDTAKSPERIODER,
    INNTEKT,
    ALDER_PÅ_BARN,
    DOKUMENTASJON_TILSYNSUTGIFTER,
    RETT_TIL_OVERGANGSSTØNAD,
    DOKUMENTASJON_AV_UTDANNING,
    ER_UTDANNING_HENSIKTSMESSIG;
}

data class ÅrsakRevurdering(
    val opplysningskilde: String,
    val årsak: String
)
