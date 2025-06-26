package no.nav.tilleggsstonader.kontrakter.arena

import no.nav.tilleggsstonader.kontrakter.arena.vedtak.Rettighet
import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class RettighetTest {
    @Test
    fun `skal mappe rettighet til stønadstype`() {
        assertThat(Rettighet.TILSYN_BARN.stønadstype).isEqualTo(Stønadstype.BARNETILSYN)
        assertThat(Rettighet.TILSYN_BARN_ARBEIDSSSØKERE.stønadstype).isEqualTo(Stønadstype.BARNETILSYN)

        assertThat(Rettighet.LÆREMIDLER.stønadstype).isEqualTo(Stønadstype.LÆREMIDLER)
        assertThat(Rettighet.LÆREMIDLER.stønadstype).isEqualTo(Stønadstype.LÆREMIDLER)
    }

    @ParameterizedTest
    @EnumSource(Stønadstype::class)
    fun `husk å definiere hvilken rettighet som skal mappes til når man legger inn en støandstype`(stønadstype: Stønadstype) {
        val finnesMapping = Rettighet.entries.any { it.stønadstype == stønadstype }

        assertThat(finnesMapping)
            .withFailMessage { "Må mappe stønadstype til Rettighet for at Arena skal kunne låse personen for nye vedtak" }
            .isTrue()
    }

    @Test
    fun `skal finne alle rettigheter for gitt stønadstype`() {
        assertThat(Rettighet.fraStønadstype(Stønadstype.BARNETILSYN))
            .containsExactlyInAnyOrder(Rettighet.TILSYN_BARN, Rettighet.TILSYN_BARN_ARBEIDSSSØKERE)
    }

    @Test
    fun `arenakoder skal være unike, då enumen skal ha en til en mapping mellom navn og arenakode`() {
        val koder = Rettighet.entries.map { it.kodeArena }
        val values: List<String> = koder.distinct()
        assertThat(values).containsExactlyInAnyOrderElementsOf(koder)
        assertThat(values).hasSize(values.size)
    }
}
