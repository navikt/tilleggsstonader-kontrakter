package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.søknad.boutgifter.fyllutsendinn.BoutgifterFyllUtSendInnData

/**
 * Søknadskjema som sendes inn fra FyllUt/SendInn
 * @param dokumentasjon fylles ikke i, men er påkrevd av [Skjema]
 */
data class SøknadsskjemaBoutgifterFyllUtSendInn(
    val language: String,
    val data: BoutgifterFyllUtSendInnData,
    override val dokumentasjon: List<DokumentasjonFelt> = emptyList(),
) : Skjema
