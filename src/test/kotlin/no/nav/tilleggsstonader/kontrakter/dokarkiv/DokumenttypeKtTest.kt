package no.nav.tilleggsstonader.kontrakter.dokarkiv

import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class DokumenttypeKtTest {
    @ParameterizedTest
    @EnumSource(Stønadstype::class)
    fun `kontroller at alle typer har riktig mapping og stønadstype`(stønadstype: Stønadstype) {
        val dokumenttyper = stønadstype.dokumenttyper
        dokumenttyper.søknad?.let { assertThat(it.name).isEqualTo("${stønadstype.name}_SØKNAD") }
        dokumenttyper.søknadVedlegg?.let { assertThat(it.name).isEqualTo("${stønadstype.name}_SØKNAD_VEDLEGG") }
        assertThat(dokumenttyper.vedtaksbrev.name).isEqualTo("${stønadstype.name}_VEDTAKSBREV")
        assertThat(dokumenttyper.frittståendeBrev.name).isEqualTo("${stønadstype.name}_FRITTSTÅENDE_BREV")
        assertThat(dokumenttyper.interntVedtak.name).isEqualTo("${stønadstype.name}_INTERNT_VEDTAK")
        assertThat(dokumenttyper.klageVedtaksbrev.name).isEqualTo("${stønadstype.name}_KLAGE_VEDTAKSBREV")
        assertThat(dokumenttyper.klageInterntVedtak.name).isEqualTo("${stønadstype.name}_KLAGE_INTERNT_VEDTAK")
    }
}
