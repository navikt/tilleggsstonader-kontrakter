package no.nav.tilleggsstonader.kontrakter.arena.sak

import no.nav.tilleggsstonader.kontrakter.arena.KodeArena

enum class Målgruppe(override val kodeArena: String, val navn: String) : KodeArena {
    ARBEIDSSØKER("ARBSOKERE", "Arbeidssøker"),
    ENSLIG_FORSØRGER_SØKER_ARBEID("ENSFORARBS", "Enslig forsørger som søker arbeid"),
    ENSLIG_FORSØRGER_UTDANNING("ENSFORUTD", "Enslig forsørger under utdanning"),
    GJENLEVENDE_SØKER_ARBEID("GJENEKARBS", "Gjenlevende ektefelle som søker arbeid"),
    GJENLEVENDE_UTDANNING("GJENEKUTD", "Gjenlevende ektefelle under utdanning"),
    DAGPENGER("MOTDAGPEN", "Mottaker av dagpenger"),
    TILTAKSPENGER("MOTTILTPEN", "Mottaker av tiltakspenger"),
    NEDSATT__ARBEIDSEVNE("NEDSARBEVN", "Person med nedsatt arbeidsevne pga. sykdom"),
    TIDLIGERE_FAMILIEPLEIER_UTDANNING("TIDLFAMPL", "Tidligere familiepleier under utdanning"),
}

enum class StatusSak(override val kodeArena: String, val navn: String) : KodeArena {
    AKTIV("AKTIV", "Aktiv"),
    LUKKET("AVSLU", "Lukket"),
    HISTORISERT("HIST", "Historisert"),
    INAKTIV("INAKT", "Inaktiv"),
    OPPRETTET("OPRTV", "Opprettet (RTV)"),
}
