package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.søknad.dagligreise.fyllutsendinn.DagligReiseFyllUtSendInnData

/**
 * Søknadskjema som sendes inn fra FyllUt/SendInn
 * @param dokumentasjon fylles ikke i, men er påkrevd av [Skjemadata]
 */
data class SøknadsskjemaDagligReiseFyllUtSendInn(
    val language: String,
    val data: DagligReiseFyllUtSendInnData,
    override val dokumentasjon: List<DokumentasjonFelt> = emptyList(),
) : Skjemadata
