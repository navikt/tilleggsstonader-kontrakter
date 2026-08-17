package no.nav.tilleggsstonader.kontrakter.dokarkiv

import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class DokumenttypeKtTest {
    @ParameterizedTest
    @EnumSource(Stønadstype::class, mode = EnumSource.Mode.EXCLUDE, names = ["REISE_TIL_SAMLING_TSO", "REISE_TIL_SAMLING_TSR"])
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

    @Nested
    inner class ReiseTilSamlingTso {
        private val stønadstype = Stønadstype.REISE_TIL_SAMLING_TSO
        private val dokumenttyper = stønadstype.dokumenttyper

        @Test
        fun `søknad bruker REISE_TIL_SAMLING-prefiks uten TSO`() {
            assertThat(dokumenttyper.søknad).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_SØKNAD)
            assertThat(dokumenttyper.søknadVedlegg).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_SØKNAD_VEDLEGG)
        }

        @Test
        fun `vedtaksbrev og brev bruker REISE_TIL_SAMLING_TSO-prefiks`() {
            assertThat(dokumenttyper.vedtaksbrev).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_TSO_VEDTAKSBREV)
            assertThat(dokumenttyper.frittståendeBrev).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_TSO_FRITTSTÅENDE_BREV)
            assertThat(dokumenttyper.interntVedtak).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_TSO_INTERNT_VEDTAK)
            assertThat(dokumenttyper.klageVedtaksbrev).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_TSO_KLAGE_VEDTAKSBREV)
            assertThat(dokumenttyper.klageInterntVedtak).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_TSO_KLAGE_INTERNT_VEDTAK)
        }
    }

    @Nested
    inner class ReiseTilSamlingTsr {
        private val stønadstype = Stønadstype.REISE_TIL_SAMLING_TSR
        private val dokumenttyper = stønadstype.dokumenttyper

        @Test
        fun `søknad bruker REISE_TIL_SAMLING-prefiks uten TSR`() {
            assertThat(dokumenttyper.søknad).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_SØKNAD)
            assertThat(dokumenttyper.søknadVedlegg).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_SØKNAD_VEDLEGG)
        }

        @Test
        fun `vedtaksbrev og brev bruker REISE_TIL_SAMLING_TSR-prefiks`() {
            assertThat(dokumenttyper.vedtaksbrev).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_TSR_VEDTAKSBREV)
            assertThat(dokumenttyper.frittståendeBrev).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_TSR_FRITTSTÅENDE_BREV)
            assertThat(dokumenttyper.interntVedtak).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_TSR_INTERNT_VEDTAK)
            assertThat(dokumenttyper.klageVedtaksbrev).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_TSR_KLAGE_VEDTAKSBREV)
            assertThat(dokumenttyper.klageInterntVedtak).isEqualTo(Dokumenttype.REISE_TIL_SAMLING_TSR_KLAGE_INTERNT_VEDTAK)
        }
    }
}
