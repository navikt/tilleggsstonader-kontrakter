package no.nav.tilleggsstonader.kontrakter

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object FileUtil {
    const val SKAL_SKRIVE_TIL_FIL = true

    fun readFile(filnavn: String): String =
        FileUtil::class.java.classLoader
            .getResource(filnavn)
            ?.readText()
            ?: error("Finner ikke fil: $filnavn")

    fun listFiles(path: String): List<Path> {
        val uri =
            FileUtil::class.java.classLoader
                .getResource(path)!!
                .toURI()
        return Files.list(Paths.get(uri)).map { it.fileName }.toList()
    }

    fun skrivTilFil(
        filnavn: String,
        data: String,
    ) {
        skrivTilFil(filnavn, data.toByteArray())
    }

    fun skrivTilFil(
        filnavn: String,
        data: ByteArray,
    ) {
        if (!SKAL_SKRIVE_TIL_FIL) {
            return
        }
        val file = File("test/resources/$filnavn")
        if (!file.exists()) {
            file.createNewFile()
        }
        file.writeBytes(data)
    }
}
