package no.nav.tilleggsstonader.kontrakter.søknad

data class SøknadskjemaDagligreise(
    override val dokumentasjon: List<DokumentasjonFelt>,
) : Skjema
