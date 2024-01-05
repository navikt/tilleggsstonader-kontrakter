package no.nav.tilleggsstonader.kontrakter.oppgave

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OppgavetypeTest {

    @Test
    fun `unike verdier`() {
        assertThat(Oppgavetype.entries.map { it.value }.toSet())
            .hasSize(Oppgavetype.entries.size)
    }
}
