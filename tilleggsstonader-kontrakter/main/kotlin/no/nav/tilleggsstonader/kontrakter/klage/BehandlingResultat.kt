package no.nav.tilleggsstonader.kontrakter.klage

enum class BehandlingResultat {
    MEDHOLD,
    IKKE_MEDHOLD,
    IKKE_MEDHOLD_FORMKRAV_AVVIST,
    IKKE_SATT,
    HENLAGT,
}

enum class BehandlingStatus {
    OPPRETTET,
    UTREDES,
    SATT_PÅ_VENT,
    VENTER,
    FERDIGSTILT,
}

enum class Årsak {
    FEIL_I_LOVANDVENDELSE,
    FEIL_REGELVERKSFORSTÅELSE,
    FEIL_ELLER_ENDRET_FAKTA,
    FEIL_PROSESSUELL,
    ANNET,
}

enum class BehandlingEventType {
    KLAGEBEHANDLING_AVSLUTTET,
    ANKEBEHANDLING_OPPRETTET,
    ANKEBEHANDLING_AVSLUTTET,
    ANKE_I_TRYGDERETTENBEHANDLING_OPPRETTET,
    BEHANDLING_FEILREGISTRERT,
    BEHANDLING_ETTER_TRYGDERETTEN_OPPHEVET_AVSLUTTET,
    OMGJOERINGSKRAVBEHANDLING_AVSLUTTET,
}

enum class KlageinstansUtfall(
    val navn: String,
) {
    TRUKKET("Trukket KA"),
    RETUR("Retur KA"),
    OPPHEVET("Opphevet KA"),
    MEDHOLD("Medhold KA"),
    DELVIS_MEDHOLD("Delvis medhold KA"),
    STADFESTELSE("Stadfestelse KA"),
    UGUNST("Ugunst (Ugyldig) KA"),
    AVVIST("Avvist KA"),
    INNSTILLING_STADFESTELSE("Innstilling om stadfestelse til trygderetten fra KA"),
    INNSTILLING_AVVIST("Innstilling om avist til trygderetten fra KA"),
    HENVIST("Henvist KA"),
    HEVET("Hevet KA"),
    MEDHOLD_ETTER_FVL_35("Medhold etter fvl. § 35 KA"),
}
