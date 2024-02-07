package no.nav.tilleggsstonader.kontrakter.arena

import no.nav.tilleggsstonader.kontrakter.arena.vedtak.Rettighet
import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RettighetTest {

    @Test
    fun `skal mappe rettighet til stønadstype`() {
        assertThat(Rettighet.TILSYN_BARN.stønadstype).isEqualTo(Stønadstype.BARNETILSYN)
        assertThat(Rettighet.TILSYN_BARN_ARBEIDSSSØKERE.stønadstype).isEqualTo(Stønadstype.BARNETILSYN)
    }
}
