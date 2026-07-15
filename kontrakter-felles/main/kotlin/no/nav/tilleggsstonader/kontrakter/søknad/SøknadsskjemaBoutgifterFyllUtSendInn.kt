package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.boutgifter.fyllutsendinn.BoutgifterFyllUtSendInnData

/**
 * Søknadskjema som sendes inn fra FyllUt/SendInn
 * @param dokumentasjon fylles ikke i, men er påkrevd av [Skjemadata]
 */
data class SøknadsskjemaBoutgifterFyllUtSendInn(
    val language: String,
    val data: BoutgifterFyllUtSendInnData,
    override val dokumentasjon: List<DokumentasjonFelt> = emptyList(),
    val formRevision: Int?,
) : Skjemadata {
    override fun getSpråkMapper(): Map<Språkkode, String> =
        mapOf(
            Språkkode.NB to "Søknad om støtte til boutgifter",
        )
}
