package no.nav.tilleggsstonader.kontrakter.felles

import tools.jackson.databind.DeserializationFeature
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.KotlinFeature
import tools.jackson.module.kotlin.jacksonMapperBuilder

object JsonMapperProvider {
    // Uten KotlinPropertyNameAsImplicitName så vil ikke properties som starter med non-ascii bokstaver bli serialisert til json'en
    private fun lagJsonMapper() =
        jacksonMapperBuilder { enable(KotlinFeature.KotlinPropertyNameAsImplicitName) }
            .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)

    val jsonMapper: JsonMapper =
        lagJsonMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build()

    val jsonMapperFailOnUnknownProperties: JsonMapper =
        lagJsonMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
            .build()
}
