package no.nav.tilleggsstonader.kontrakter.felles

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TemaTest {
    @Nested
    inner class GjelderTemaTilleggsstønader {
        @Test
        fun `TSO gjelder tema tilleggsstønader`() {
            assertThat(Tema.gjelderTemaTilleggsstønader("TSO")).isTrue
            assertThat(Tema.gjelderTemaTilleggsstønader("tso")).isTrue
        }

        @Test
        fun `TSR gjelder tema tilleggsstønader`() {
            assertThat(Tema.gjelderTemaTilleggsstønader("TSR")).isTrue
            assertThat(Tema.gjelderTemaTilleggsstønader("tsr")).isTrue
        }

        @Test
        fun `null gjelder ikke tema tilleggsstønader`() {
            assertThat(Tema.gjelderTemaTilleggsstønader(null)).isFalse()
        }

        @Test
        fun `ENF gjelder ikke tema tilleggsstønader`() {
            assertThat(Tema.gjelderTemaTilleggsstønader("ENF")).isFalse()
            assertThat(Tema.gjelderTemaTilleggsstønader("enf")).isFalse()
        }
    }
}
