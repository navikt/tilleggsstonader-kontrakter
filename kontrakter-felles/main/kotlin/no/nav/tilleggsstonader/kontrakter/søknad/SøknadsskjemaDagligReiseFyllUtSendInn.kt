package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.søknad.dagligReise.fyllutsendinn.DagligReiseFyllUtSendInnData

/**
 * Søknadskjema som sendes inn fra FyllUt/SendInn
 * @param dokumentasjon fylles ikke i, men er påkrevd av [Skjema]
 */
data class SøknadsskjemaDagligReiseFyllUtSendInn(
    val language: String,
    val data: DagligReiseFyllUtSendInnData,
    override val dokumentasjon: List<DokumentasjonFelt> = emptyList(),
) : Skjema
