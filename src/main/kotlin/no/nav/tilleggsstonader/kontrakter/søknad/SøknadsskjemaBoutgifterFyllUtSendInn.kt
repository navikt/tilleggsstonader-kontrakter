package no.nav.tilleggsstonader.kontrakter.søknad

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.tilleggsstonader.kontrakter.søknad.boutgifter.fyllutsendinn.BoutgifterFyllUtSendInnData

/**
 * Søknadskjema som sendes inn fra FyllUt/SendInn
 * @param dokumentasjon fylles ikke i, men er påkrevd av [Skjema]
 */
@JsonIgnoreProperties("language", ignoreUnknown = false)
data class SøknadsskjemaBoutgifterFyllUtSendInn(
    val data: BoutgifterFyllUtSendInnData,
    override val dokumentasjon: List<DokumentasjonFelt> = emptyList(),
) : Skjema
