package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.søknad.dagligreise.fyllutsendinn.DagligreiseFyllUtSendInnData

/**
 * Søknadskjema som sendes inn fra FyllUt/SendInn
 * @param dokumentasjon fylles ikke i, men er påkrevd av [Skjema]
 */
data class SøknadsskjemaDagligreiseFyllUtSendInn(
    val language: String,
    val data: DagligreiseFyllUtSendInnData,
    val dokumentasjon: List<DokumentasjonFelt> = emptyList(),
)
// TODO: må kommenteres inn når vi skal ta denne i bruk i Sak
//    override val dokumentasjon: List<DokumentasjonFelt> = emptyList(),
// ) : Skjema
