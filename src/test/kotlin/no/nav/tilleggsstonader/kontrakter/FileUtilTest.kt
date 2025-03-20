package no.nav.tilleggsstonader.kontrakter

import no.nav.tilleggsstonader.kontrakter.FileUtil.SKAL_SKRIVE_TIL_FIL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FileUtilTest {
    @Test
    fun `SKAL_SKRIVE_TIL_FIL skal være false`() {
        assertThat(SKAL_SKRIVE_TIL_FIL).isFalse()
    }
}
