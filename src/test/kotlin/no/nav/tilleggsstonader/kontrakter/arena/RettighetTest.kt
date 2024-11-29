package no.nav.tilleggsstonader.kontrakter.arena

import no.nav.tilleggsstonader.kontrakter.arena.vedtak.Rettighet
import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class RettighetTest {

    @Test
    fun `skal mappe rettighet til stønadstype`() {
        assertThat(Rettighet.TILSYN_BARN.stønadstype).isEqualTo(Stønadstype.BARNETILSYN)
        assertThat(Rettighet.TILSYN_BARN_ARBEIDSSSØKERE.stønadstype).isEqualTo(Stønadstype.BARNETILSYN)

        assertThat(Rettighet.LÆREMIDLER.stønadstype).isEqualTo(Stønadstype.LÆREMIDLER)
        assertThat(Rettighet.LÆREMIDLER.stønadstype).isEqualTo(Stønadstype.LÆREMIDLER)
    }

    @Test
    fun `skal feile hvis stønadstype for rettighet ikke er mappet`() {
        assertThatThrownBy {
            Rettighet.REISE.stønadstype
        }.hasMessage("Har ikke lagt inn mapping av stønadstype for REISE")
    }
}
